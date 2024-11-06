package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.dto.ChallengeDto;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class ChallengeServiceImpl implements ChallengeService {
    private ChallengeDao challengeDao;
    public ChallengeServiceImpl(ChallengeDao challengeDao) {
        this.challengeDao = challengeDao;
    }

    @Override
    @Transactional
    public boolean createChallenge(Challenge challenge) {
        return challengeDao.insertChallenge(challenge) > 0 ? true : false;
    }

    @Override
    public List<Challenge> getChallengeByStatusAndUserId(long userId, ChallengeStatus status) {
        return challengeDao.selectChallengesByStatusAndUserId(userId, status.name());
    }

    @Override
    public Challenge getChallengeDetail(long id) {
        return challengeDao.selectChallenge(id);
    }

    @Override
    public boolean modifyChallenge(long id, ChallengeDto challengeDto) {
        Challenge Challenge = findChallenge(id);
        modifyChallengeFields(challengeDto, Challenge);

        int result = challengeDao.updateChallenge(Challenge);
        if (result == 0) {
            throw new IllegalStateException("챌린지 업데이트에 실패했습니다.");
        }
        return true;
    }

    private static void modifyChallengeFields(ChallengeDto challengeDto, Challenge Challenge) {
        Challenge.updateType(challengeDto.getType());
        Challenge.updateStartDt(challengeDto.getStartDt());
        Challenge.updateEndDt(challengeDto.getEndDt());
        Challenge.updateGoalCnt(challengeDto.getGoalCnt());
    }

    private Challenge findChallenge(long challengeId) {
        Challenge foundChallenge = getChallengeDetail(challengeId);
        if (foundChallenge == null) {
            throw new NoSuchElementException("없는 챌린지 입니다.");
        }
        return foundChallenge;
    }

    @Override
    @Transactional
    public boolean removeChallenge(long id) {
        challengeDao.deleteTodayChallengeByChallengeId(id);
        log.info("delete todaychallenge by challengeId");
        return challengeDao.deleteChallenge(id) > 0 ? true : false;
    }

    @Override
    public boolean existsChallengeById(long id) {
        return challengeDao.existsChallengeById(id);
    }

    @Override
    public boolean modifyChallengeAchieveCnt(long id) {
        return challengeDao.updateChallengeAchieveCnt(id) > 0;
    }

    @Override
    public List<Challenge> findChallengesWithoutTodayRecord(LocalDate yesterday) {
        return challengeDao.findChallengesWithoutTodayRecord(yesterday);
    }

    @Override
    @Scheduled(cron = "50 59 23 * * ?")
    public void modifyChallengeStatus() {
        LocalDate today = LocalDate.now();
        if (challengeDao.existsChallengeByStartDate(today) || challengeDao.existsChallengeByEndDate(today)) {
            int changedCount = challengeDao.updateChallengeStatus(today);
            log.info("updated challenge count : {}", changedCount);
        }
    }
}
