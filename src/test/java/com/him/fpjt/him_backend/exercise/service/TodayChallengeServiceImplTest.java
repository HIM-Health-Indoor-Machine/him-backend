package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import com.him.fpjt.him_backend.user.service.AttendenceService;
import com.him.fpjt.him_backend.user.service.UserService;
import java.time.LocalDate;
import java.util.Arrays;
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
    @Mock
    private AttendenceService attendenceService;
    @InjectMocks
    private TodayChallengeServiceImpl todayChallengeService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("진행중인 챌린지들을 모두 조회하여, 오늘 날짜의 오늘의 챌린지를 생성한다.")
    public void createTodayChallenge_success() {
        List<Long> allChallengeIds = Arrays.asList(1L, 2L, 3L);
        when(challengeService.getAllChallengeId()).thenReturn(allChallengeIds);
        when(todayChallengeDao.insertTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);

        todayChallengeService.createTodayChallenge();

        verify(challengeService, times(1)).getAllChallengeId();
        verify(todayChallengeDao, times(allChallengeIds.size())).insertTodayChallenge(any(TodayChallenge.class));
    }

    @Test
    @DisplayName("오늘의 챌린지 생성 실패 시, 예외가 발생한다.")
    void createTodayChallenge_fail() {
        List<Long> allChallengeIds = Arrays.asList(1L, 2L, 3L);
        when(challengeService.getAllChallengeId()).thenReturn(allChallengeIds);
        when(todayChallengeDao.insertTodayChallenge(any(TodayChallenge.class))).thenReturn(0L);

        assertThrows(RuntimeException.class, () -> todayChallengeService.createTodayChallenge());
    }

    @Test
    @DisplayName("오늘의 챌린지가 성공적으로 조회될 경우, 조회 결과를 반환한다.")
    void getTodayChallengeById_success() {
        TodayChallenge todayChallenge = new TodayChallenge(1L, 10L, 1L, LocalDate.now(), true);
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
    void modifyTodayChallenge_getDailyExp() throws Exception {
        Challenge challenge = new Challenge(1L, "제목1", ChallengeStatus.ONGOING, ExerciseType.SQUAT,
                LocalDate.now().minusDays(100), LocalDate.now(), 10L, 0, 1L);

        TodayChallenge todayChallenge = new TodayChallenge(1L, 10L, 1L, LocalDate.now(), false);
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(1L, 10L, 1L, LocalDate.now());
        when(todayChallengeDao.selectTodayChallengeById(todayChallengeDto.getId())).thenReturn(todayChallenge);
        when(todayChallengeDao.updateTodayChallenge(todayChallenge)).thenReturn(1L);
        when(challengeService.getChallengeDetail(todayChallenge.getChallengeId())).thenReturn(challenge);

        when(todayChallengeDao.updateIsAchieved(todayChallenge.getId())).thenReturn(1L);
        doNothing().when(attendenceService).setAttendanceStatus(anyLong(), any(LocalDate.class));
        doNothing().when(userService).modifyUserExp(anyLong(), anyInt());

        boolean result = todayChallengeService.modifyTodayChallenge(todayChallengeDto);

        assertTrue(result);
        verify(userService).modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_ACHIVEMENT_EXP);
        verify(todayChallengeDao, times(1)).updateIsAchieved(todayChallenge.getId());
        verify(attendenceService, times(1)).setAttendanceStatus(challenge.getUserId(), LocalDate.now());
    }
    @Test
    @DisplayName("7일 내내 오늘의 챌린지 목표 달성 시, 경험치 10EXP를 추가한다.")
    void modifyTodayChallenge_get7DaysExp() throws Exception {
        long challengeId = 1L;
        long userId = 1L;
        Challenge challenge = new Challenge(challengeId, "제목1", ChallengeStatus.ONGOING, ExerciseType.SQUAT,
                LocalDate.now().minusDays(10), LocalDate.now(), 10L, 0, userId);
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(1L, 10L, challengeId, LocalDate.now());

        when(todayChallengeDao.selectTodayChallengeById(anyLong()))
                .thenReturn(new TodayChallenge(1L, 10L, challengeId, LocalDate.now(), false));

        when(todayChallengeDao.updateTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);
        when(challengeService.getChallengeDetail(challengeId)).thenReturn(challenge);
        when(todayChallengeDao.checkAchievementBonus(challengeId, LocalDate.now(), ExpPoints.SEVEN_DAY)).thenReturn(true);
        when(todayChallengeDao.checkAchievementBonus(challengeId, LocalDate.now(), ExpPoints.THIRTY_DAY)).thenReturn(false);
        doNothing().when(userService).modifyUserExp(anyLong(), anyInt());

        boolean result = todayChallengeService.modifyTodayChallenge(todayChallengeDto);

        verify(userService, times(1)).modifyUserExp(userId, ExpPoints.SEVEN_DAY_STREAK_EXP);
        verify(userService, never()).modifyUserExp(userId, ExpPoints.THIRTY_DAY_STREAK_EXP);
        verify(userService, times(1)).modifyUserExp(userId, ExpPoints.DAILY_ACHIVEMENT_EXP);
    }
    @Test
    @DisplayName("8일 내내 오늘의 챌린지 목표 달성 시, 경험치 5EXP를 추가한다.")
    void modifyTodayChallenge_getDailyExp_when8daysStreak() throws Exception {
        long challengeId = 1L;
        long userId = 1L;
        Challenge challenge = new Challenge(challengeId, "제목1", ChallengeStatus.ONGOING, ExerciseType.SQUAT,
                LocalDate.now().minusDays(100), LocalDate.now(), 10L, 0, userId);
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(1L, 10L, challengeId, LocalDate.now());

        TodayChallenge todayChallenge = new TodayChallenge(1L, 10L, challengeId, LocalDate.now(), false);
        when(todayChallengeDao.selectTodayChallengeById(anyLong())).thenReturn(todayChallenge);

        when(todayChallengeDao.updateTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);
        when(challengeService.getChallengeDetail(challengeId)).thenReturn(challenge);
        when(todayChallengeDao.checkAchievementBonus(challengeId, LocalDate.now(), ExpPoints.SEVEN_DAY)).thenReturn(false);
        when(todayChallengeDao.checkAchievementBonus(challengeId, LocalDate.now(), ExpPoints.THIRTY_DAY)).thenReturn(false);
        when(todayChallengeDao.updateIsAchieved(anyLong())).thenReturn(1L);
        doNothing().when(userService).modifyUserExp(anyLong(), anyInt());

        boolean result = todayChallengeService.modifyTodayChallenge(todayChallengeDto);

        verify(userService, times(1)).modifyUserExp(userId, ExpPoints.DAILY_ACHIVEMENT_EXP);
        verify(userService, never()).modifyUserExp(userId, ExpPoints.SEVEN_DAY_STREAK_EXP);
    }
    @Test
    @DisplayName("30일 내내 오늘의 챌린지 목표 달성 시, 경험치 100EXP를 추가한다.")
    void modifyTodayChallenge_get30DaysExp() throws Exception {
        long challengeId = 1L;
        long userId = 1L;

        Challenge challenge = new Challenge(challengeId, "30일 챌린지", ChallengeStatus.ONGOING, ExerciseType.PUSHUP,
                LocalDate.now().minusDays(100), LocalDate.now(), 10L, 0, userId);
        TodayChallengeDto todayChallengeDto = new TodayChallengeDto(1L, 10L, challengeId, LocalDate.now());

        TodayChallenge todayChallenge = new TodayChallenge(1L, 10L, challengeId, LocalDate.now(), false);

        when(todayChallengeDao.selectTodayChallengeById(anyLong())).thenReturn(todayChallenge);
        when(todayChallengeDao.updateTodayChallenge(any(TodayChallenge.class))).thenReturn(1L);
        when(challengeService.getChallengeDetail(challengeId)).thenReturn(challenge);
        when(todayChallengeDao.checkAchievementBonus(challengeId, LocalDate.now(), ExpPoints.SEVEN_DAY)).thenReturn(false);
        when(todayChallengeDao.checkAchievementBonus(challengeId, LocalDate.now(), ExpPoints.THIRTY_DAY)).thenReturn(true);
        when(todayChallengeDao.updateIsAchieved(anyLong())).thenReturn(1L);
        doNothing().when(userService).modifyUserExp(anyLong(), anyInt());

        boolean result = todayChallengeService.modifyTodayChallenge(todayChallengeDto);

        assertTrue(result);

        verify(userService, times(1)).modifyUserExp(userId, ExpPoints.THIRTY_DAY_STREAK_EXP);
        verify(userService, never()).modifyUserExp(userId, ExpPoints.SEVEN_DAY_STREAK_EXP);
    }
    @Test
    @DisplayName("오늘의 챌린지 목표를 달성하지 못했을 시, 경험치 3EXP를 차감한다.")
    void modifyUnachievementTodayChallenge() throws Exception {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Challenge challenge = new Challenge(1L, "제목1",ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now(), LocalDate.now(), 10L, 0,1L);
        TodayChallenge unachievedTodayChallenge = new TodayChallenge(1L, 5L, 1L, yesterday, false);

        when(todayChallengeDao.findUnachievedChallenges(yesterday)).thenReturn(List.of(unachievedTodayChallenge));
        when(challengeService.getChallengeDetail(unachievedTodayChallenge.getChallengeId())).thenReturn(challenge);
        doNothing().when(userService).modifyUserExp(anyLong(), anyInt());

        todayChallengeService.modifyUnachievementTodayChallenge();
        verify(userService).modifyUserExp(challenge.getUserId(), ExpPoints.DAILY_PENALTY_EXP);
    }
}