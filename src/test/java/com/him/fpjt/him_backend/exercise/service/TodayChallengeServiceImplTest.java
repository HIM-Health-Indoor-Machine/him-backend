package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.exercise.dao.TodayChallengeDao;
import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;
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

        // when & then: 예외 검증
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
}
