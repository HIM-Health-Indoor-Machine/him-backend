package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;

public interface TodayChallengeService {
    public long createTodayChallenge(TodayChallenge todayChallenge);
    public TodayChallenge getTodayChallengeById(long id);
    public boolean modifyTodayChallenge(TodayChallengeDto todayChallengeDto) throws Exception;
    public void modifyUnachievementTodayChallenge() throws Exception;
}
