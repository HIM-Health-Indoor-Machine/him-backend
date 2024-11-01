package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.GameDao;
import com.him.fpjt.him_backend.exercise.domain.DifficultyLevel;
import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;
    private final UserService userService;

    public GameServiceImpl(GameDao gameDao) {
    public GameServiceImpl(GameDao gameDao, UserService userService) {
        this.gameDao = gameDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public boolean createGame(Game game) {
        return gameDao.insertGame(game) != 0;
    public void createGame(Game game) throws Exception {
        int result = gameDao.insertGame(game);  // 게임 데이터 삽입
        if (result == 0) {
            throw new Exception("게임 생성에 실패했습니다.");
        }
    }

    // 동일 조건을 만족하는 게임 중 다른 성공 기록이 없는 경우 경험치를 반영하는 메서드.
    // 현재 게임을 성공으로 수정.
    @Override
    @Transactional
    public boolean modifyGame(int id) {
        return gameDao.updateGame(id) != 0;
    public void applyUserExp(long gameId, long userId) throws Exception {
        Game currentGame = gameDao.findGameById(gameId);

        boolean hasSimilarAchievement = gameDao.existsAchievedGame(
                currentGame.getDate(),
                currentGame.getType().name(),
                currentGame.getDifficultyLevel().name(),
                userId);

        if (!hasSimilarAchievement) {
            long expPoints = gameExpPoints(currentGame.getDifficultyLevel());
            userService.updateUserExp(userId, expPoints);
        }

        int updateResult = gameDao.updateGameAchievement(gameId);
        if (updateResult == 0) {
            throw new Exception("게임 성공 상태 업데이트에 실패했습니다.");
        }
    }

}    private long gameExpPoints(DifficultyLevel difficultyLevel) {
    private long gameExpPoints(DifficultyLevel difficultyLevel) {
        return switch (difficultyLevel) {
            case EASY -> 5;
            case MEDIUM -> 10;
            case HARD -> 20;
        };
    }
}
