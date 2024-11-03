package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.common.constants.ExpPoints;
import com.him.fpjt.him_backend.exercise.dao.TodayChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;
import com.him.fpjt.him_backend.user.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TodayChallengeServiceImplTest {
    @Mock
    private TodayChallengeDao todayChallengeDao;
    @Mock
    private ChallengeService challengeService;
    @Mock
    private UserService userService;
    @InjectMocks
    private TodayChallengeServiceImpl todayChallengeService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("오늘의 챌린지 생성 성공 시 todayChalllengeId를 반환한다.")
    public void createTodayChallenge_success() {
        TodayChallenge todayChallenge = new TodayChallenge(0, 1L, LocalDate.now());

        when(challengeService.existsChallengeById(1L)).thenReturn(true);
        when(todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(1L, LocalDate.now())).thenReturn(false);
        when(todayChallengeDao.insertTodayChallenge(todayChallenge)).thenReturn(1L);

        long resultId = todayChallengeService.createTodayChallenge(todayChallenge);

        assertEquals(1L, resultId);
        verify(todayChallengeDao).insertTodayChallenge(todayChallenge);
    }
    @Test
    @DisplayName("존재하지 않는 챌린지 id의 경우, 예외가 발생합니다.")
    public void createTodayChallenge_notExistChallengeId() {
        TodayChallenge todayChallenge = new TodayChallenge(0, 1L, LocalDate.now());
        when(challengeService.existsChallengeById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> todayChallengeService.createTodayChallenge(todayChallenge),
                "존재하지 않는 챌린지 id 입니다."
        );
    }
    @Test
    @DisplayName("이미 오늘의 챌린지가 있는 경우, 예외가 발생한다.")
    void createTodayChallenge_duplicate() {
        TodayChallenge todayChallenge = new TodayChallenge(0, 1L, LocalDate.now());
        when(challengeService.existsChallengeById(1L)).thenReturn(true);
        when(todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(1L, LocalDate.now())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> todayChallengeService.createTodayChallenge(todayChallenge));
    }

    @Test
    @DisplayName("오늘의 챌린지 생성 실패 시, 예외가 발생한다.")
    void createTodayChallenge_fail() {
        TodayChallenge todayChallenge = new TodayChallenge(0, 1L, LocalDate.now());
        when(todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(1L, LocalDate.now())).thenReturn(false);
        when(todayChallengeDao.insertTodayChallenge(todayChallenge)).thenReturn(0L);

        assertThrows(RuntimeException.class, () -> todayChallengeService.createTodayChallenge(todayChallenge));
    }

    @Test
    @DisplayName("오늘의 챌린지가 성공적으로 조회될 경우, 조회 결과를 반환한다.")
    void getTodayChallengeById_success() {
        TodayChallenge todayChallenge = new TodayChallenge(1L, 10L, 1L, LocalDate.now());
        when(todayChallengeDao.selectTodayChallengeById(1L)).thenReturn(todayChallenge);

        TodayChallenge result = todayChallengeService.getTodayChallengeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(todayChallengeDao).selectTodayChallengeById(1L);
    }

    @Test
    @DisplayName("오늘의 챌린지가 존재하지 않는 경우, 예외가 발생한다.")
    void getTodayChallengeById_NotFoundError() {
        when(todayChallengeDao.selectTodayChallengeById(1L)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> todayChallengeService.getTodayChallengeById(1L));
    }

    @Test
    @DisplayName("오늘의 챌린지를 달성할 경우, 경험치 5EXP를 추가한다.")
    void modifyTodayChallenge_getDailyExp() {
        Challenge challenge = new Challenge(1L, ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now().minusDays(100), LocalDate.now(), 10L, 0,1L);
        TodayChallenge todayChallenge = new TodayChallenge(1L, 10L, 1L, LocalDate.now());
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(1L, 10L, 1L, LocalDate.now());

        when(todayChallengeDao.selectTodayChallengeById(todayChallengeDto.getId())).thenReturn(todayChallenge);
        when(todayChallengeDao.updateTodayChallenge(todayChallenge)).thenReturn(1L);
        when(challengeService.getChallengeDetail(todayChallenge.getChallengeId())).thenReturn(challenge);
        when(userService.modifyUserExp(anyLong(), anyInt())).thenReturn(true);

        assertTrue(todayChallengeService.modifyTodayChallenge(todayChallengeDto));

        verify(userService).modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_ACHIVEMENT_EXP);
    }
    @Test
    @DisplayName("7일 내내 오늘의 챌린지 목표 달성 시, 경험치 10EXP를 추가한다.")
    void modifyTodayChallenge_get7DaysExp() {
        Challenge challenge = new Challenge(1L, ChallengeStatus.ONGOING, ExerciseType.SQUAT,
                LocalDate.now().minusDays(100), LocalDate.now(), 10, 0, 1L);
        for (int i = 0; i < 7; i++) {
            TodayChallenge todayChallenge = new TodayChallenge(i, 10, 1L, LocalDate.now().minusDays(i));
            when(todayChallengeDao.selectTodayChallengeById(todayChallenge.getId())).thenReturn(todayChallenge);
        }
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(0, 10, 1L, LocalDate.now());

        when(todayChallengeDao.updateTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);
        when(challengeService.getChallengeDetail(todayChallengeDto.getChallengeId())).thenReturn(challenge);
        when(todayChallengeDao.checkAchievementBonus(challenge.getId(), LocalDate.now(), ExpPoints.SEVEN_DAY)).thenReturn(true);
        when(todayChallengeDao.checkAchievementBonus(challenge.getId(), LocalDate.now(), ExpPoints.THIRTY_DAY)).thenReturn(false);

        assertTrue(todayChallengeService.modifyTodayChallenge(todayChallengeDto));

        verify(userService, times(1)).modifyUserExp(challenge.getUserId(), ExpPoints.SEVEN_DAY_STREAK_EXP);
    }
    @Test
    @DisplayName("8일 내내 오늘의 챌린지 목표 달성 시, 경험치 5EXP를 추가한다.")
    void modifyTodayChallenge_getDailyExp_when8daysStreak() {
        Challenge challenge = new Challenge(1L, ChallengeStatus.ONGOING, ExerciseType.SQUAT,
                LocalDate.now().minusDays(100), LocalDate.now(), 10L, 0, 1L);
        for (int i = 1; i <= 8; i++) {
            TodayChallenge todayChallenge = new TodayChallenge(i, 10L, challenge.getId(), LocalDate.now().minusDays(i));
            when(todayChallengeDao.selectTodayChallengeById(todayChallenge.getId())).thenReturn(todayChallenge);
        }
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(1L, 10L, challenge.getId(), LocalDate.now());

        when(todayChallengeDao.updateTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);
        when(challengeService.getChallengeDetail(todayChallengeDto.getChallengeId())).thenReturn(challenge);
        when(todayChallengeDao.checkAchievementBonus(challenge.getId(), LocalDate.now(), ExpPoints.SEVEN_DAY)).thenReturn(false);
        when(todayChallengeDao.checkAchievementBonus(challenge.getId(), LocalDate.now(), ExpPoints.THIRTY_DAY)).thenReturn(false);

        assertTrue(todayChallengeService.modifyTodayChallenge(todayChallengeDto));

        verify(userService, times(1)).modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_ACHIVEMENT_EXP);
        verify(userService, never()).modifyUserExp(challenge.getUserId(), ExpPoints.SEVEN_DAY_STREAK_EXP);
        verify(userService, never()).modifyUserExp(challenge.getUserId(), ExpPoints.THIRTY_DAY_STREAK_EXP);
    }
    @Test
    @DisplayName("30일 내내 오늘의 챌린지 목표 달성 시, 경험치 100EXP를 추가한다.")
    void modifyTodayChallenge_get30DaysExp() {
        Challenge challenge = new Challenge(1L, ChallengeStatus.ONGOING, ExerciseType.SQUAT,
                LocalDate.now().minusDays(100), LocalDate.now(), 10L, 0, 1L);
        for (int i = 0; i < 30; i++) {
            TodayChallenge todayChallenge = new TodayChallenge(i, 10L, challenge.getId(), LocalDate.now().minusDays(i));
            when(todayChallengeDao.selectTodayChallengeById(todayChallenge.getId())).thenReturn(todayChallenge);
        }
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(0, 10L, challenge.getId(), LocalDate.now());

        when(todayChallengeDao.updateTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);
        when(challengeService.getChallengeDetail(todayChallengeDto.getChallengeId())).thenReturn(challenge);
        when(todayChallengeDao.checkAchievementBonus(challenge.getId(), LocalDate.now(), ExpPoints.SEVEN_DAY)).thenReturn(false);
        when(todayChallengeDao.checkAchievementBonus(challenge.getId(), LocalDate.now(), ExpPoints.THIRTY_DAY)).thenReturn(true);

        assertTrue(todayChallengeService.modifyTodayChallenge(todayChallengeDto));

        verify(userService, times(1)).modifyUserExp(challenge.getUserId(), ExpPoints.THIRTY_DAY_STREAK_EXP);
        verify(userService, times(1)).modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_ACHIVEMENT_EXP);
        verify(userService, never()).modifyUserExp(challenge.getUserId(), ExpPoints.SEVEN_DAY_STREAK_EXP);
    }
    @Test
    @DisplayName("오늘의 챌린지 목표를 달성하지 못했을 시, 경험치 3EXP를 차감한다.")
    void modifyUnachievementTodayChallenge() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Challenge challenge = new Challenge(1L, ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now(), LocalDate.now(), 10L, 0,1L);
        TodayChallenge unachievedTodayChallenge = new TodayChallenge(1L, 5L, 1L, yesterday);

        when(todayChallengeDao.findUnachievedChallenges(yesterday)).thenReturn(List.of(unachievedTodayChallenge));
        when(challengeService.getChallengeDetail(unachievedTodayChallenge.getChallengeId())).thenReturn(challenge);
        when(userService.modifyUserExp(anyLong(), eq(ExpPoints.DAILY_PENALTY_EXP))).thenReturn(true);

        todayChallengeService.modifyUnachievementTodayChallenge();
        verify(userService).modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_PENALTY_EXP);
    }
}