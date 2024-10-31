package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.util.Map;

public interface TodayChallengeDao {
    public long insertTodayChallenge(TodayChallenge todayChallenge);
    public boolean existsTodayChallengeByChallengeIdAndDate(Map<String, Object> map);
}
