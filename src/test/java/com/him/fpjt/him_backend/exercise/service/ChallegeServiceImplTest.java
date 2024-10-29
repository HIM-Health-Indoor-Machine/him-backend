package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.exercise.dao.ChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;
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
                1L
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
}
