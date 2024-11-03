package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.dto.GameDto;
import com.him.fpjt.him_backend.exercise.service.GameService;
import com.him.fpjt.him_backend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        try {
            gameService.createGame(game);
            return ResponseEntity.ok("게임이 성공적으로 추가되었습니다.");
        } catch (IllegalArgumentException e) {  // 유효하지 않은 입력 값일 때 발생
            return new ResponseEntity<>("게임 생성 실패: 유효하지 않은 입력입니다. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedOperationException e) {  // 지원되지 않는 작업일 때 발생
            return new ResponseEntity<>("게임 생성 실패: 지원되지 않는 작업입니다. " + e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            return new ResponseEntity<>("알 수 없는 오류로 인해 게임 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> achieveGame(@RequestBody GameDto gameDto) {
        try {
            if (gameDto.isAchieved()) {
                gameService.applyUserExp(gameDto.getGameId());
                return ResponseEntity.ok("조건에 따른 경험치 반영이 완료되었습니다.");
            } else {
                return ResponseEntity.ok("성취하지 않은 상태이므로 경험치가 반영되지 않았습니다.");
            }
        } catch (NoSuchElementException e) {  // 게임을 찾을 수 없을 때 발생
            return new ResponseEntity<>("경험치 반영 실패: 게임을 찾을 수 없습니다. " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {  // 상태가 적절하지 않을 때 발생
            return new ResponseEntity<>("경험치 반영 실패: 상태가 적절하지 않습니다. " + e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("알 수 없는 오류로 인해 경험치 반영에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
