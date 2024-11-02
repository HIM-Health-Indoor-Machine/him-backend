package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.common.constants.ExpPoints;
import com.him.fpjt.him_backend.exercise.dao.TodayChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.user.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodayChallengeServiceImpl implements TodayChallengeService {
    private TodayChallengeDao todayChallengeDao;
    private ChallengeService challengeService;
    private UserService userService;

    public TodayChallengeServiceImpl(TodayChallengeDao todayChallengeDao,
            ChallengeService challengeService, UserService userService) {
        this.todayChallengeDao = todayChallengeDao;
        this.challengeService = challengeService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public long createTodayChallenge(TodayChallenge todayChallenge) {
        if(!challengeService.existsChallengeById(todayChallenge.getChallengeId())) {
            throw new IllegalArgumentException("존재하지 않는 챌린지 id 입니다.");
        }
        if (isTodayChallengeExists(todayChallenge.getChallengeId(), todayChallenge.getDate())) {
            throw new IllegalStateException("이미 동일한 챌린지가 존재합니다.");
        }
        long todayChallengeId = todayChallengeDao.insertTodayChallenge(todayChallenge);
        if (todayChallengeId <= 0) {
            throw new RuntimeException("챌린지 생성에 실패했습니다.");
        }
        return todayChallengeId;
    }
    @Override
    public TodayChallenge getTodayChallengeById(long id) {
        return Optional.ofNullable(todayChallengeDao.selectTodayChallengeById(id))
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 오늘의 챌린지가 존재하지 않습니다."));
    }

    private boolean isTodayChallengeExists(long challengeId, LocalDate date) {
        return todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(challengeId, date);
    }
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    @Override
    public void modifyUnachievementTodayChallenge() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<TodayChallenge> unachievedTodayChallenges = todayChallengeDao.findUnachievedChallenges(yesterday);

        for (TodayChallenge unachievedTodayChallenge : unachievedTodayChallenges) {
            userService.modifyUserExp(challengeService.getChallengeDetail(unachievedTodayChallenge.getChallengeId()).getUserId(), ExpPoints.DAILY_PENALTY_EXP);
        }

        List<Challenge> missingChallenges = challengeService.findChallengesWithoutTodayRecord(yesterday);

        for (Challenge missingChallenge : missingChallenges) {
            userService.modifyUserExp(missingChallenge.getUserId(), ExpPoints.DAILY_PENALTY_EXP);
        }
    }
}