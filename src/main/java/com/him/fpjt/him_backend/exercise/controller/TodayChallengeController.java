package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;
import com.him.fpjt.him_backend.exercise.service.TodayChallengeService;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/today-challenge")
public class TodayChallengeController {
    private TodayChallengeService todayChallengeService;

    public TodayChallengeController(TodayChallengeService todayChallengeService) {
        this.todayChallengeService = todayChallengeService;
    }

    @PostMapping
    public ResponseEntity<Object> createTodayChallenge(
            @RequestBody TodayChallengeDto todayChallengeDto) {
        TodayChallenge todayChallenge = new TodayChallenge(todayChallengeDto.getCnt(), todayChallengeDto.getChallengeId(), todayChallengeDto.getDate());
        try {
            long todayChallengeId = todayChallengeService.createTodayChallenge(todayChallenge);
            return ResponseEntity.status(HttpStatus.CREATED).body(todayChallengeId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("{todayChallengeId}")
    public ResponseEntity<Object> getTodayChallenge(@PathVariable("todayChallengeId") long id) {
        try {
            TodayChallenge todayChallenge = todayChallengeService.getTodayChallengeById(id);
            return ResponseEntity.ok().body(todayChallenge);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
}
