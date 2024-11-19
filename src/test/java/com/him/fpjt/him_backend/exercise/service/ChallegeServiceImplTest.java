package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.ChallengeDto;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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
                "제목1",
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
                LocalDate.now(),
                true
        );
    }

    @Test
    @DisplayName("챌린지 생성 성공 시에 true를 반환한다.")
    public void createChallenge_success() {
        Challenge challenge = new Challenge("하루 10분 스쿼트", ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now(), LocalDate.now(), 10L, 1L);

        when(challengeDao.insertChallenge(challenge)).thenReturn(1);

        assertDoesNotThrow(() -> challengeService.createChallenge(challenge));
        verify(challengeDao).insertChallenge(challenge);
    }

    @Test
    @DisplayName("챌린지 생성 실패 시에 false를 반환한다.")
    public void createChallenge_fail() {
        Challenge challenge = new Challenge("하루 10분 스쿼트", ChallengeStatus.ONGOING, ExerciseType.SQUAT, LocalDate.now(), LocalDate.now(), 10L, 1L);

        when(challengeDao.insertChallenge(challenge)).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> challengeService.createChallenge(challenge));
        verify(challengeDao).insertChallenge(challenge);
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
    @DisplayName("존재하는 챌린지를 성공적으로 업데이트")
    void modifyChallenge_successfulUpdate() {
        long challengeId = 1L;
        ChallengeDto challengeDto = new ChallengeDto(ExerciseType.SQUAT, LocalDate.now(), LocalDate.now().plusDays(30), 50);
        Challenge challenge = new Challenge(challengeId, "하루 10분 스쿼트", ChallengeStatus.ONGOING, ExerciseType.PUSHUP, LocalDate.now().minusDays(10), LocalDate.now().plusDays(31), 30, 4, 1L);

        when(challengeDao.selectChallenge(challengeId)).thenReturn(challenge);
        when(challengeDao.updateChallenge(any(Challenge.class))).thenReturn(1);

        assertDoesNotThrow(() -> challengeService.modifyChallenge(challengeId, challengeDto));
        verify(challengeDao).updateChallenge(any(Challenge.class));
    }

    @Test
    @DisplayName("없는 챌린지를 업데이트하려고 할 때 NoSuchElementException 발생")
    void modifyChallenge_noSuchElementException() {
        long challengeId = 1L;
        ChallengeDto challengeDto = new ChallengeDto(ExerciseType.SQUAT, LocalDate.now(), LocalDate.now().plusDays(10), 100);

        when(challengeDao.selectChallenge(challengeId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            challengeService.modifyChallenge(challengeId, challengeDto);
        });
    }

    @Test
    @DisplayName("업데이트가 실패할 때 IllegalStateException 발생")
    void modifyChallenge_illegalStateException() {
        long challengeId = 1L;
        ChallengeDto challengeDto = new ChallengeDto(ExerciseType.PUSHUP, LocalDate.now(),
                LocalDate.now().plusDays(10), 100);
        Challenge challenge = new Challenge(challengeId, "하루 10분 푸쉬업", ChallengeStatus.ONGOING, ExerciseType.PUSHUP, LocalDate.now().minusDays(10), LocalDate.now().plusDays(31), 30, 4, 1L);

        when(challengeDao.selectChallenge(challengeId)).thenReturn(challenge);
        when(challengeDao.updateChallenge(any(Challenge.class))).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> {
            challengeService.modifyChallenge(challengeId, challengeDto);
        });
    }

    @Test
    @DisplayName("챌린지 삭제 성공 시에 true를 반환한다.")
    public void removeChallenge_success() {
        when(challengeDao.deleteTodayChallengeByChallengeId(1L)).thenReturn(1);
        when(challengeDao.deleteChallenge(1L)).thenReturn(1);

        assertDoesNotThrow(() -> challengeService.removeChallenge(1L));

        verify(challengeDao).deleteTodayChallengeByChallengeId(1L);
        verify(challengeDao).deleteChallenge(1L);
    }

    @Test
    @DisplayName("챌린지 삭제 실패 시에 false를 반환한다.")
    public void removeChallenge_fail() {
        when(challengeDao.deleteTodayChallengeByChallengeId(1L)).thenReturn(1);
        when(challengeDao.deleteChallenge(1L)).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> challengeService.removeChallenge(1L));
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