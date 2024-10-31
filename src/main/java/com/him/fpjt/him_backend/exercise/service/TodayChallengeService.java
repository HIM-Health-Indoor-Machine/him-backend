package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;

public interface TodayChallengeService {
    public long createTodayChallenge(TodayChallenge todayChallenge);
    public TodayChallenge getTodayChallengeById(long id);
}
