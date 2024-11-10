package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;
import com.him.fpjt.him_backend.exercise.service.TodayChallengeService;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping("{todayChallengeId}")
    public ResponseEntity<Object> getTodayChallenge(@PathVariable("todayChallengeId") long id) {
        try {
            TodayChallenge todayChallenge = todayChallengeService.getTodayChallengeById(id);
            return ResponseEntity.ok().body(todayChallenge);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<Object> modifyTodayChallenge(
                                                        @RequestBody TodayChallengeDto todayChallengeDto) {
        try {
            todayChallengeService.modifyTodayChallenge(todayChallengeDto);
            return ResponseEntity.ok("오늘의 챌린지가 성공적으로 수정되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 오늘의 챌린지를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
