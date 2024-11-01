package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Game;

import java.util.List;

public interface GameService {

    public boolean createGame(Game game);

    public boolean modifyGame(int id);
    void createGame(Game game) throws Exception;

    void applyUserExp(long gameId) throws Exception;
}
