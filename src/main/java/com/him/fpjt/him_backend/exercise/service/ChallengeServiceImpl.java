package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import java.time.LocalDate;
import java.util.List;
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
}
