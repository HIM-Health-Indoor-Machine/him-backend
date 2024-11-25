package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.GameDao;
import com.him.fpjt.him_backend.exercise.domain.DifficultyLevel;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    @Mock
    private GameDao gameDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_success() {
        Game game = new Game(1L, LocalDate.now(), ExerciseType.SQUAT, DifficultyLevel.EASY, false, 1L);
        when(gameDao.insertGame(game)).thenReturn(1);

        assertDoesNotThrow(() -> gameService.createGame(game));
        verify(gameDao, times(1)).insertGame(game);
    }

    @Test
    void createGame_failure() {
        Game game = new Game(1L, LocalDate.now(), ExerciseType.SQUAT, DifficultyLevel.EASY, false, 1L);
        when(gameDao.insertGame(game)).thenReturn(0);

        Exception exception = assertThrows(Exception.class, () -> gameService.createGame(game));
        verify(gameDao, times(1)).insertGame(game);
    }

    @Test
    void applyUserExp_noSimilarAchievement_andExpUpdateSuccess() {
        long gameId = 1L;
        long userId = 1L;
        Game game = new Game(gameId, LocalDate.now(), ExerciseType.SQUAT, DifficultyLevel.HARD, false, userId);

        when(gameDao.findGameById(gameId)).thenReturn(game);
        when(gameDao.existsAchievedGame(any(LocalDate.class), eq("SQUAT"), eq("HARD"), eq(userId))).thenReturn(false);
        when(userDao.updateUserExp(eq(userId), anyLong())).thenReturn(1);
        when(gameDao.updateGameAchievement(gameId)).thenReturn(1);

        assertDoesNotThrow(() -> gameService.applyUserExp(gameId));
        verify(userDao, times(1)).updateUserExp(eq(userId), anyLong());
        verify(gameDao, times(1)).updateGameAchievement(gameId);
    }

    @Test
    void applyUserExp_noSimilarAchievement_andExpUpdateFails() {
        long gameId = 1L;
        long userId = 1L;
        Game game = new Game(gameId, LocalDate.now(), ExerciseType.SQUAT, DifficultyLevel.HARD, false, userId);

        when(gameDao.findGameById(gameId)).thenReturn(game);
        when(gameDao.existsAchievedGame(any(LocalDate.class), eq("SQUAT"), eq("HARD"), eq(userId))).thenReturn(false);
        when(userDao.updateUserExp(eq(userId), anyLong())).thenReturn(0);

        // 검증: 경험치 업데이트가 실패하여 예외가 발생하는지 확인
        Exception exception = assertThrows(Exception.class, () -> gameService.applyUserExp(gameId));

        // verify - updateUserExp는 호출되었지만, updateGameAchievement는 호출되지 않음
        verify(userDao, times(1)).updateUserExp(eq(userId), anyLong());
        verify(gameDao, never()).updateGameAchievement(gameId);
    }


    @Test
    void applyUserExp_withSimilarAchievement() {
        long gameId = 1L;
        long userId = 1L;
        Game game = new Game(gameId, LocalDate.now(), ExerciseType.SQUAT, DifficultyLevel.HARD, false, userId);

        when(gameDao.findGameById(gameId)).thenReturn(game);
        when(gameDao.existsAchievedGame(any(LocalDate.class), eq("SQUAT"), eq("HARD"), eq(userId))).thenReturn(true);
        when(gameDao.updateGameAchievement(gameId)).thenReturn(1);

        assertDoesNotThrow(() -> gameService.applyUserExp(gameId));
        verify(userDao, never()).updateUserExp(eq(userId), anyLong());
        verify(gameDao, times(1)).updateGameAchievement(gameId);
    }

    @Test
    void applyUserExp_gameNotFound() {
        long gameId = 1L;
        when(gameDao.findGameById(gameId)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> gameService.applyUserExp(gameId));
        verify(gameDao, never()).updateGameAchievement(gameId);
    }

}
