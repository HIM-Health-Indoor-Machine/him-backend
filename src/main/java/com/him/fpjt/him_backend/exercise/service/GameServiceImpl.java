package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.dao.GameDao;
import com.him.fpjt.him_backend.exercise.domain.DifficultyLevel;
import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.dto.GameDto;
import com.him.fpjt.him_backend.user.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;
    private final UserDao userDao;

    public GameServiceImpl(GameDao gameDao, UserDao userDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void createGame(Game game) throws Exception {
        int result = gameDao.insertGame(game);
        if (result == 0) {
            throw new Exception("게임 생성에 실패했습니다.");
        }
    }

    @Override
    @Transactional
    public void applyUserExp(long gameId) {
        Game currentGame = validateGameExists(gameId);
        long userId = currentGame.getUserId();

        if (!checkForSimilarAchievements(currentGame, userId)) {
            long expPoints = calculateExpPoints(currentGame.getDifficultyLevel());
            updateUserExperience(userId, expPoints);
        }

        updateGameAchievementStatus(gameId);
    }

    private Game validateGameExists(long gameId) {
        Game game = gameDao.findGameById(gameId);
        if (game == null) {
            throw new NoSuchElementException("게임을 찾을 수 없습니다.");
        }
        return game;
    }

    private boolean checkForSimilarAchievements(Game currentGame, long userId) {
        return gameDao.existsAchievedGame(
                currentGame.getDate(),
                currentGame.getType().name(),
                currentGame.getDifficultyLevel().name(),
                userId);
    }

    private void updateUserExperience(long userId, long expPoints) {
        int expUpdateResult = userDao.updateUserExp(userId, expPoints);
        if (expUpdateResult == 0) {
            throw new UnsupportedOperationException("사용자 경험치 업데이트에 실패했습니다.");
        }
    }

    private void updateGameAchievementStatus(long gameId) {
        int updateResult = gameDao.updateGameAchievement(gameId);
        if (updateResult == 0) {
            throw new IllegalStateException("게임 성공 상태 업데이트에 실패했습니다.");
        }
    }

    private long calculateExpPoints(DifficultyLevel difficultyLevel) {
        return switch (difficultyLevel) {
            case EASY -> ExpPoints.GAME_EASY_MODE;
            case MEDIUM -> ExpPoints.GAME_MEDIUM_MODE;
            case HARD -> ExpPoints.GAME_HARD_MODE;
        };
    }
}
