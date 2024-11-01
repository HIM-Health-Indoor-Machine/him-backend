package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Game;

import java.util.List;

public interface GameService {

    public boolean createGame(Game game);
    void createGame(Game game) throws Exception;

    public boolean modifyGame(int id);
    void applyUserExp(long gameId, long userId) throws Exception;

}
