package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.dto.GameDto;

import java.time.LocalDate;

public interface GameDao {

    int insertGame(Game game);

    Game findGameById(long gameId);

    boolean existsAchievedGame(LocalDate date, String type, String difficultyLevel, long userId);

    int updateGameAchievement(long id);
}
