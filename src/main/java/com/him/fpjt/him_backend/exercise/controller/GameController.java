package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.dto.GameDto;
import com.him.fpjt.him_backend.exercise.service.GameService;
import com.him.fpjt.him_backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin("*")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Long> createGame(@RequestBody Game game) {
        long gameId = gameService.createGame(game);
        return ResponseEntity.ok(gameId);
    }

    @PutMapping
    public ResponseEntity<String> achieveGame(@RequestBody GameDto gameDto) {
        if (gameDto.isAchieved()) {
            gameService.applyUserExp(gameDto.getGameId());
            return ResponseEntity.ok("조건에 따른 경험치 반영이 완료되었습니다.");
        } else {
            return ResponseEntity.ok("성취하지 않은 상태이므로 경험치가 반영되지 않았습니다.");
        }
    }
}