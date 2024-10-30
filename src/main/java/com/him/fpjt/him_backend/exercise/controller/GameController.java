package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Game;
import com.him.fpjt.him_backend.exercise.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        boolean isSave = gameService.createGame(game);
        return isSave ?
                new ResponseEntity<String>("Game added successfully", HttpStatus.OK) :
                new ResponseEntity<String>("Failed to add game", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<String> modifyGame(@PathVariable("gameId") int id) {
        boolean isSave = gameService.modifyGame(id);
        return isSave ?
                new ResponseEntity<String>("Game updated successfully", HttpStatus.OK) :
                new ResponseEntity<String>("Failed to update game", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
