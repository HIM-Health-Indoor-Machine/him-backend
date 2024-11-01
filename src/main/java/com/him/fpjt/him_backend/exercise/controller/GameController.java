package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.dto.GameDto;
import com.him.fpjt.him_backend.exercise.service.GameService;
import com.him.fpjt.him_backend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        boolean isSave = gameService.createGame(game);
        return isSave ?
                new ResponseEntity<String>("Game added successfully", HttpStatus.OK) :
                new ResponseEntity<String>("Failed to add game", HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            gameService.createGame(game);
            return ResponseEntity.ok("게임이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            return new ResponseEntity<>("게임 추가 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<String> modifyGame(@PathVariable("gameId") int id) {
        boolean isSave = gameService.modifyGame(id);
        return isSave ?
                new ResponseEntity<String>("Game updated successfully", HttpStatus.OK) :
                new ResponseEntity<String>("Failed to update game", HttpStatus.INTERNAL_SERVER_ERROR);
    @PutMapping
    public ResponseEntity<String> achieveGame(@RequestBody GameDto gameDto) {
        try {
            if (gameDto.isAchieved()) {
                gameService.applyUserExp(gameDto.getGameId());
                return ResponseEntity.ok("조건에 따른 경험치 반영이 완료되었습니다.");
            } else {
                return ResponseEntity.ok("성취하지 않은 상태이므로 경험치가 반영되지 않았습니다.");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("경험치 반영 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
