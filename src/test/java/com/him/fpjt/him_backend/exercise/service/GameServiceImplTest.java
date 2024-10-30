package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.GameDao;
import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import com.him.fpjt.him_backend.exercise.domain.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceImplTest {

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_success() {
        // Given
        Game game = new Game(ExerciseType.SQUAT, DifficultyLevel.EASY, false, 1);
        when(gameDao.insertGame(game)).thenReturn(1);

        // When
        boolean result = gameService.createGame(game);

        // Then
        assertTrue(result);
        verify(gameDao, times(1)).insertGame(game);
    }

    @Test
    void createGame_failure() {
        // Given
        Game game = new Game(ExerciseType.PUSHUP, DifficultyLevel.MEDIUM, false, 2);
        when(gameDao.insertGame(game)).thenReturn(0);

        // When
        boolean result = gameService.createGame(game);

        // Then
        assertFalse(result);
        verify(gameDao, times(1)).insertGame(game);
    }

    @Test
    void modifyGame_success() {
        // Given
        int gameId = 1;
        when(gameDao.updateGame(gameId)).thenReturn(1);

        // When
        boolean result = gameService.modifyGame(gameId);

        // Then
        assertTrue(result);
        verify(gameDao, times(1)).updateGame(gameId);
    }

    @Test
    void modifyGame_failure() {
        // Given
        int gameId = 2;
        when(gameDao.updateGame(gameId)).thenReturn(0);

        // When
        boolean result = gameService.modifyGame(gameId);

        // Then
        assertFalse(result);
        verify(gameDao, times(1)).updateGame(gameId);
    }
}
