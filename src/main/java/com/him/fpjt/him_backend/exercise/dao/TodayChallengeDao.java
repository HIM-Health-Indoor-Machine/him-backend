package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;
import java.util.List;

public interface TodayChallengeDao {
    public long insertTodayChallenge(TodayChallenge todayChallenge);
    public TodayChallenge selectTodayChallengeById(long id);
    public TodayChallenge selectTodayChallengeByChallengeIdAndDate(long challengeId, LocalDate date);
    public List<TodayChallenge> selectTodayChallengeByUserIdAndDate(long userId, LocalDate date);
    public long updateTodayChallenge(TodayChallenge todayChallenge);
    public boolean checkAchievementBonus(long challengeId, LocalDate date, int days);
    public List<TodayChallenge> findUnachievedChallenges(LocalDate yesterday);
    public List<TodayChallenge> selectTodayChallengeByUserIdAndDateRange(long userId, LocalDate startOfMonth, LocalDate endOfMonth);
    public long updateIsAchieved(long id);
}
