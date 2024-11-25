package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.dto.ChallengeDto;
import com.him.fpjt.him_backend.exercise.service.ChallengeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge")
@CrossOrigin(origins = "http://localhost:5173")
public class ChallengeController {
    private ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
    @PostMapping
    public ResponseEntity<?> createChallenge(@RequestBody Challenge challenge) {
        long challengeId = challengeService.createChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).body(challengeId);
    }
    @GetMapping
    public ResponseEntity<?> getChallengeByStatusAndUserId(@RequestParam(value = "userId", defaultValue = "true") long userId,
                                                        @RequestParam("status") String status) {
        ChallengeStatus challengeStatus;
        try {
            challengeStatus = ChallengeStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 상태 값입니다.");
        }
        List<Challenge> challenges = challengeService.getChallengeByStatusAndUserId(userId, challengeStatus);
        return ResponseEntity.ok().body(challenges);
    }
    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getChallengeDetail(@PathVariable("challengeId") long id) {
        Challenge challenges = challengeService.getChallengeDetail(id);
        return ResponseEntity.ok().body(challenges);
    }
    @PutMapping("/{challengeId}")
    public ResponseEntity<String> modifyChallenge(@PathVariable("challengeId") long id,
                                            @RequestBody ChallengeDto challenge) {
        challengeService.modifyChallenge(id, challenge);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<String> removeChallenge(@PathVariable("challengeId") long id) {
        challengeService.removeChallenge(id);
        return ResponseEntity.ok().build();
    }
}