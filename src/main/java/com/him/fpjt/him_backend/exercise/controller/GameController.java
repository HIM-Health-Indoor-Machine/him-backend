package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.dto.GameDto;
import com.him.fpjt.him_backend.exercise.service.GameService;
import com.him.fpjt.him_backend.user.service.UserService;
import java.time.LocalDate;
import java.util.List;
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
    public ResponseEntity<Object> achieveGame(@RequestBody GameDto gameDto) {
        if (gameDto.isAchieved()) {
            long expPoints = gameService.applyUserExp(gameDto.getGameId());
            return ResponseEntity.ok(expPoints);
        } else {
            return ResponseEntity.ok("성취하지 않은 상태이므로 경험치가 반영되지 않았습니다.");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMonthlyGame(@PathVariable("userId") long userId,
                                            @RequestParam("year") int year,
                                            @RequestParam("month") int month) {
        List<Game> games = gameService.getMonthlyGame(userId, year, month);
        return ResponseEntity.ok().body(games);
    }
    @GetMapping("/list")
    public ResponseEntity<Object> getGames(@RequestParam(value = "userId") long userId, @RequestParam(value = "date") LocalDate date) {
        List<Game> games = gameService.getGameByUserIdAndDate(userId, date);
        return ResponseEntity.ok().body(games);
    }
}