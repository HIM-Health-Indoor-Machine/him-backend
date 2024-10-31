package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.exercise.dao.TodayChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TodayChallengeServiceImplTest {
    @Mock
    private TodayChallengeDao todayChallengeDao;
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
        when(todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(anyMap())).thenReturn(false);
        when(todayChallengeDao.insertTodayChallenge(todayChallenge)).thenReturn(1L);

        long resultId = todayChallengeService.createTodayChallenge(todayChallenge);

        assertEquals(1L, resultId);
        verify(todayChallengeDao).insertTodayChallenge(todayChallenge);
    }
    @Test
    @DisplayName("이미 오늘의 챌린지가 있는 경우, 예외가 발생한다.")
    void createTodayChallenge_duplicate() {
        TodayChallenge todayChallenge = new TodayChallenge(0, 1L, LocalDate.now());
        when(todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(anyMap())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> todayChallengeService.createTodayChallenge(todayChallenge));
    }

    @Test
    @DisplayName("오늘의 챌린지 생성 실패 시, 예외가 발생한다.")
    void createTodayChallenge_fail() {
        TodayChallenge todayChallenge = new TodayChallenge(0, 1L, LocalDate.now());
        when(todayChallengeDao.existsTodayChallengeByChallengeIdAndDate(anyMap())).thenReturn(false);
        when(todayChallengeDao.insertTodayChallenge(todayChallenge)).thenReturn(0L);

        assertThrows(RuntimeException.class, () -> todayChallengeService.createTodayChallenge(todayChallenge));
    }
}
