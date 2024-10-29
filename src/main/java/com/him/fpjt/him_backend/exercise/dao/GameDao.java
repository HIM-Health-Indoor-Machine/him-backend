package com.him.fpjt.him_backend.exercise.dao;

import com.him.fpjt.him_backend.exercise.domain.Game;

import java.util.List;

//@Mapper
public interface GameDao {

    public int insertGame(Game game);

    public int updateGame(int id);

}
