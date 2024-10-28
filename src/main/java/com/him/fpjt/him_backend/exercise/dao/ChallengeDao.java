package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

public interface ChallengeDao {
    public int insertChallenge(Challenge challenge);

    public List<Challenge> selectOngoingChallenges(Map map);
    public List<Challenge> selectDoneChallenges(long userId);
    public Challenge selectChallenge(long id);
    public int deleteChallenge(long id);
}
