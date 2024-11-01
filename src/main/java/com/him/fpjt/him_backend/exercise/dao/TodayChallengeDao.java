package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;

public interface TodayChallengeDao {
    public long insertTodayChallenge(TodayChallenge todayChallenge);
    public boolean existsTodayChallengeByChallengeIdAndDate(long challengeId, LocalDate date);
    public TodayChallenge selectTodayChallengeById(long id);
    public long updateTodayChallenge(TodayChallenge todayChallenge);
}
