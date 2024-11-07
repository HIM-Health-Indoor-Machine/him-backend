package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import java.time.LocalDate;
import java.util.List;

public interface ChallengeDao {
    public int insertChallenge(Challenge challenge);
    public List<Challenge> selectChallengesByStatusAndUserId(long userId, String status);
    public Challenge selectChallenge(long id);
    public int deleteChallenge(long id);
    public int deleteTodayChallengeByChallengeId(long id);
    public boolean existsChallengeById(long id);
    public int updateChallengeAchieveCnt(long id);
    public boolean existsChallengeByStartDate(LocalDate date);
    public boolean existsChallengeByEndDate(LocalDate date);
    public int updateChallengeStatus(LocalDate today);
    public List<Challenge> findChallengesWithoutTodayRecord(LocalDate yesterday);
    public int updateChallenge(Challenge challenge);
    public List<Long> selectAllChallengeId();
}