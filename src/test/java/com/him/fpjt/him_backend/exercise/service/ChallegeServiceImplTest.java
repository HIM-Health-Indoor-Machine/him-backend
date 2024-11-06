package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ChallegeServiceImplTest {
    @Mock
    private ChallengeDao challengeDao;

    @InjectMocks
    private ChallengeServiceImpl challengeService;
    private Challenge mockChallenge;
    private TodayChallenge mockTodayChallenge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockChallenge = new Challenge(
                ChallengeStatus.ONGOING,
                ExerciseType.SQUAT,
                LocalDate.now(),
                LocalDate.now(),
                10L,
                1L
        );
        mockTodayChallenge = new TodayChallenge(
                mockChallenge.getId(),
                10L,
                1,
                LocalDate.now()
        );
    }

    @Test
    @DisplayName("챌린지 생성 성공 시에 true를 반환한다.")
    public void createChallenge_success() {
        Challenge challenge = new Challenge(ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now(), LocalDate.now(), 10L, 1L);
        when(challengeDao.insertChallenge(challenge)).thenReturn(1);
        assertTrue(challengeService.createChallenge(challenge));
    }

    @Test
    @DisplayName("챌린지 생성 실패 시에 false를 반환한다.")
    public void createChallenge_fail() {
        Challenge challenge = new Challenge(ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now(), LocalDate.now(), 10L, 1L);
        when(challengeDao.insertChallenge(challenge)).thenReturn(0);
        assertFalse(challengeService.createChallenge(challenge));
    }

    @Test
    @DisplayName("status와 userId에 따라 챌린지 목록을 조회한다.")
    void getChallengeByStatusAndUserId() {
        long userId = 1L;
        String status = ChallengeStatus.ONGOING.name();
        when(challengeDao.selectChallengesByStatusAndUserId(userId, status)).thenReturn(List.of(mockChallenge));

        List<Challenge> challenges = challengeService.getChallengeByStatusAndUserId(userId, ChallengeStatus.ONGOING);

        assertEquals(1, challenges.size());
        verify(challengeDao, times(1)).selectChallengesByStatusAndUserId(userId, status);
    }

    @Test
    @DisplayName("챌린지 id에 따라 챌린지를 조회한다.")
    void getChallengeDetail() {
        when(challengeDao.selectChallenge(1L)).thenReturn(mockChallenge);

        Challenge challenge = challengeService.getChallengeDetail(1L);

        assertNotNull(challenge);
        verify(challengeDao, times(1)).selectChallenge(1L);
    }
    @Test
    @DisplayName("챌린지 삭제 성공 시에 true를 반환한다.")
    public void removeChallenge_success() {
        when(challengeDao.deleteTodayChallengeByChallengeId(1L)).thenReturn(1);
        when(challengeDao.deleteChallenge(1L)).thenReturn(1);
        assertTrue(challengeService.removeChallenge(1L));
    }

    @Test
    @DisplayName("챌린지 삭제 실패 시에 false를 반환한다.")
    public void removeChallenge_fail() {
        when(challengeDao.deleteTodayChallengeByChallengeId(1L)).thenReturn(1);
        when(challengeDao.deleteChallenge(1L)).thenReturn(0);
        assertFalse(challengeService.removeChallenge(1L));
    }

    @Test
    @DisplayName("종료일이 오늘인 챌린지의 상태를 done으로 업데이트 한다.")
    void modifyChallengeStatus_done() {
        LocalDate today = LocalDate.now();
        when(challengeDao.existsChallengeByStartDate(today)).thenReturn(false);
        when(challengeDao.existsChallengeByEndDate(today)).thenReturn(true);

        when(challengeDao.updateChallengeStatus(today)).thenReturn(3);

        challengeService.modifyChallengeStatus();

        verify(challengeDao).existsChallengeByStartDate(today);
        verify(challengeDao).existsChallengeByEndDate(today);
        verify(challengeDao).updateChallengeStatus(today);
    }

    @Test
    @DisplayName("시작일이 오늘인 챌린지의 상태를 ongoing으로 업데이트 한다.")
    void modifyChallengeStatus_onging() {
        LocalDate today = LocalDate.now();
        when(challengeDao.existsChallengeByStartDate(today)).thenReturn(true);
        when(challengeDao.existsChallengeByEndDate(today)).thenReturn(false);

        when(challengeDao.updateChallengeStatus(today)).thenReturn(3);

        challengeService.modifyChallengeStatus();

        verify(challengeDao).existsChallengeByStartDate(today);
        verify(challengeDao).updateChallengeStatus(today);
    }
}