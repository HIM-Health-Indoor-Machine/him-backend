package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Game;

public interface GameService {

    long createGame(Game game);

    long applyUserExp(long gameId);
}
