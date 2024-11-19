package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.Game;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import java.time.LocalDate;
import java.util.List;

public interface GameDao {

    int insertGame(Game game);

    Game findGameById(long gameId);

    boolean existsAchievedGame(LocalDate date, String type, String difficultyLevel, long userId);

    int updateGameAchievement(long id);
    List<Game> selectGameByUserIdAndDateRange(long userId, LocalDate startOfMonth, LocalDate endOfMonth);
    List<Game> selectGameByUserIdAndDate(long userId, LocalDate date);
}
