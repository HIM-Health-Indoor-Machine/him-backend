package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import java.util.List;
import java.util.Map;

public interface ChallengeDao {
    public int insertChallenge(Challenge challenge);
    public Challenge selectChallenge(long id);
    public int deleteChallenge(long id);
    public int deleteTodayChallengeByChallengeId(long id);
}