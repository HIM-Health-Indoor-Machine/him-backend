package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;

public interface TodayChallengeService {
    public void createTodayChallenge();
    public TodayChallenge getTodayChallengeById(long id);
    public boolean modifyTodayChallenge(TodayChallengeDto todayChallengeDto);
    public void modifyUnachievementTodayChallenge() throws Exception;
}
