package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Game;
import java.util.List;

public interface GameService {

    long createGame(Game game);

    long applyUserExp(long gameId);
    List<Game> getMonthlyGame(long userId, int year, int month);
}
