package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Game;

public interface GameService {

    void createGame(Game game) throws Exception;

    void applyUserExp(long gameId) throws Exception;
}
