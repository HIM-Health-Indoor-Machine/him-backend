package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    private ChallengeDao challengeDao;
    public ChallengeServiceImpl(ChallengeDao challengeDao) {
        this.challengeDao = challengeDao;
    }

    @Override
    public boolean createChallenge(Challenge challenge) {
        return challengeDao.insertChallenge(challenge) > 0 ? true : false;
    }

}
