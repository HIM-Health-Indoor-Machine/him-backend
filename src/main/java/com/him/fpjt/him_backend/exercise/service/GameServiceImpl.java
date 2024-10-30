package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.GameDao;
import com.him.fpjt.him_backend.exercise.domain.Game;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;

    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    @Transactional
    public boolean createGame(Game game) {
        return gameDao.insertGame(game) != 0;
    }

    @Override
    @Transactional
    public boolean modifyGame(int id) {
        return gameDao.updateGame(id) != 0;
    }

}