package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import lombok.extern.slf4j.Slf4j;
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
    @Transactional
    public boolean removeChallenge(long id) {
        challengeDao.deleteTodayChallengeByChallengeId(id);
        log.info("delete todaychallenge by challengeId");
        return challengeDao.deleteChallenge(id) > 0 ? true : false;
    }
}