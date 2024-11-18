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
    public void createChallenge(Challenge challenge) {
        int result = challengeDao.insertChallenge(challenge);
        if (result == 0) {
            throw new IllegalStateException("챌린지 저장에 실패했습니다.");
        }
    }

    @Override
    public List<Challenge> getChallengeByStatusAndUserId(long userId, ChallengeStatus status) {
        List<Challenge> challenges = challengeDao.selectChallengesByStatusAndUserId(userId,
                status.name());
        if (challenges.isEmpty()) {
            throw new NoSuchElementException("챌린지가 없습니다.");
        }
        return challenges;
    }

    @Override
    public Challenge getChallengeDetail(long id) {
        Challenge challenge = challengeDao.selectChallenge(id);
        if (challenge == null) {
            throw new NoSuchElementException("일치하는 챌린지가 없습니다.");
        }
        return challenge;
    }

    @Override
    public void modifyChallenge(long id, ChallengeDto challengeDto) {
        Challenge Challenge = findChallenge(id);
        modifyChallengeFields(challengeDto, Challenge);

        int result = challengeDao.updateChallenge(Challenge);
        if (result == 0) {
            throw new IllegalStateException("챌린지 업데이트에 실패했습니다.");
        }
    }

    private static void modifyChallengeFields(ChallengeDto challengeDto, Challenge Challenge) {
        Challenge.updateTitle(Challenge.getTitle());
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
    public void removeChallenge(long id) {
        challengeDao.deleteTodayChallengeByChallengeId(id);
        log.info("delete todaychallenge by challengeId");
        int result = challengeDao.deleteChallenge(id);
        if (result == 0) {
            throw new IllegalStateException("챌린지 삭제에 실패했습니다.");
        }
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
    @Scheduled(cron = "10 0 0 * * ?")
    public void modifyChallengeStatus() {
        LocalDate today = LocalDate.now();
        if (challengeDao.existsChallengeByStartDate(today) || challengeDao.existsChallengeByEndDate(today)) {
            int changedCount = challengeDao.updateChallengeStatus(today);
            log.info("updated challenge count : {}", changedCount);
        }
    }

    @Override
    public List<Long> getAllChallengeId() {
        return challengeDao.selectAllChallengeId();
    }
}
