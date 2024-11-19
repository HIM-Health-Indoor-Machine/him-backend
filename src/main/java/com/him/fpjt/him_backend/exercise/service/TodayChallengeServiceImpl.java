package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.common.constants.ExpPoints;
import com.him.fpjt.him_backend.exercise.dao.TodayChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;
import com.him.fpjt.him_backend.user.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
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

    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    @Override
    public void createTodayChallenge() {
        List<Long> allChallengeId = challengeService.getAllChallengeId();
        for (Long challengeId : allChallengeId) {
            long todayChallengeId = todayChallengeDao.insertTodayChallenge(new TodayChallenge(0, challengeId, LocalDate.now(), false));
            if (todayChallengeId <= 0) {
                throw new IllegalStateException("챌린지 생성에 실패했습니다.");
            }
        }
    }
    @Override
    public TodayChallenge getTodayChallengeById(long id) {
        return Optional.ofNullable(todayChallengeDao.selectTodayChallengeById(id))
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 오늘의 챌린지가 존재하지 않습니다."));
    }

    @Override
    public TodayChallenge getTodayChallengeByChallengeIdAndDate(long challengeId, LocalDate date) {
        return Optional.ofNullable(todayChallengeDao.selectTodayChallengeByChallengeIdAndDate(challengeId, date))
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 오늘의 챌린지가 존재하지 않습니다."));
    }

    @Transactional
    @Override
    public boolean modifyTodayChallenge(TodayChallengeDto newTodayChallengeDto){
        TodayChallenge todayChallenge = getTodayChallengeById(newTodayChallengeDto.getId());
        todayChallenge.setCnt(newTodayChallengeDto.getCnt());
        boolean isUpdated = todayChallengeDao.updateTodayChallenge(todayChallenge) > 0;
        Challenge challenge = challengeService.getChallengeDetail(todayChallenge.getChallengeId());

        if (isUpdated && isGoalAchieved(todayChallenge)) {
            log.info("todayChallenge update successful, and goal achieved");
            challengeService.modifyChallengeAchieveCnt(todayChallenge.getChallengeId());

            addAchievementExp(todayChallenge, challenge);
            todayChallengeDao.updateIsAchieved(todayChallenge.getId());
        }
        return isUpdated;
    }

    private void addAchievementExp(TodayChallenge todayChallenge, Challenge challenge){
        int bonusExp = calculateAchievementBonusExp(todayChallenge.getChallengeId(), todayChallenge.getDate());
        if (bonusExp > 0) {
            userService.modifyUserExp(challenge.getUserId(), bonusExp);
        }
        userService.modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_ACHIVEMENT_EXP);
    }

    private boolean isGoalAchieved(TodayChallenge todayChallenge) {
        long goalCnt = challengeService.getChallengeDetail(todayChallenge.getChallengeId()).getGoalCnt();
        return todayChallenge.getCnt() >= goalCnt;
    }

    private int calculateAchievementBonusExp(long challengeId, LocalDate currentDate) {
        boolean isSevenDayStreak = todayChallengeDao.checkAchievementBonus(challengeId, currentDate, ExpPoints.SEVEN_DAY);
        boolean isThirtyDayStreak = todayChallengeDao.checkAchievementBonus(challengeId, currentDate, ExpPoints.THIRTY_DAY);

        if (isThirtyDayStreak) return ExpPoints.THIRTY_DAY_STREAK_EXP;
        if (isSevenDayStreak) return ExpPoints.SEVEN_DAY_STREAK_EXP;
        return 0;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @Override
    public void modifyUnachievementTodayChallenge() throws Exception {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<TodayChallenge> unachievedTodayChallenges = todayChallengeDao.findUnachievedChallenges(yesterday);

        for (TodayChallenge unachievedTodayChallenge : unachievedTodayChallenges) {
            userService.modifyUserExp(challengeService.getChallengeDetail(unachievedTodayChallenge.getChallengeId()).getUserId(), ExpPoints.DAILY_PENALTY_EXP);
        }
    }
}