package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;
import java.time.LocalDate;
import java.util.List;

public interface TodayChallengeService {
    public void createTodayChallenge();
    public TodayChallenge getTodayChallengeById(long id);
    public TodayChallenge getTodayChallengeByChallengeIdAndDate(long challengeId, LocalDate date);
    public boolean modifyTodayChallenge(TodayChallengeDto todayChallengeDto);
    public void modifyUnachievementTodayChallenge() throws Exception;
    List<TodayChallenge> getMonthlyTodayChallenge(long userId, int year, int month);
}
