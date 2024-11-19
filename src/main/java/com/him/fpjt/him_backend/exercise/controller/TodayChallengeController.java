package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.TodayChallenge;
import com.him.fpjt.him_backend.exercise.dto.TodayChallengeDto;
import com.him.fpjt.him_backend.exercise.service.TodayChallengeService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/today-challenge")
@CrossOrigin(origins = "http://localhost:5173")
public class TodayChallengeController {
    private TodayChallengeService todayChallengeService;

    public TodayChallengeController(TodayChallengeService todayChallengeService) {
        this.todayChallengeService = todayChallengeService;
    }
    @GetMapping
    public ResponseEntity<Object> getTodayChallenge(@RequestParam(value = "challengeId") long challengeId,
            @RequestParam(value = "date") LocalDate date) {
        TodayChallenge todayChallenge = todayChallengeService.getTodayChallengeByChallengeIdAndDate(challengeId, date);
        return ResponseEntity.ok().body(todayChallenge);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getTodayChallenges(@RequestParam(value = "userId") long userId, @RequestParam(value = "date") LocalDate date) {
        List<TodayChallenge> todayChallenges = todayChallengeService.getTodayChallengeByUserIdAndDate(userId, date);
        return ResponseEntity.ok().body(todayChallenges);
    }
    @PutMapping
    public ResponseEntity<Object> modifyTodayChallenge(@RequestBody TodayChallengeDto todayChallengeDto) {
        todayChallengeService.modifyTodayChallenge(todayChallengeDto);
        return ResponseEntity.ok("오늘의 챌린지가 성공적으로 수정되었습니다.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMonthlyTodayChallenge(@PathVariable("userId") long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<TodayChallenge> todayChallenges = todayChallengeService.getMonthlyTodayChallenge(userId, year, month);
        return ResponseEntity.ok().body(todayChallenges);
    }
}