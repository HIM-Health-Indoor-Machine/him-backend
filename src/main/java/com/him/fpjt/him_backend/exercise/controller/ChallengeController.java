package com.him.fpjt.him_backend.exercise.controller;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.service.ChallengeService;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {
    private ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
    @PostMapping
    public ResponseEntity<String> createChallenge(@RequestBody Challenge challenge) {
        boolean isSave = challengeService.createChallenge(challenge);
        return isSave == true ?
                ResponseEntity.status(HttpStatus.CREATED).build():
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("챌린지 저장에 실패했습니다.");
    }
    @GetMapping
    public ResponseEntity<?> getChallengeByStatusAndUserId(@RequestParam(value = "userId", defaultValue = "true") long userId,
                                                        @RequestParam("status") String status) {
        ChallengeStatus challengeStatus;
        try {
            challengeStatus = ChallengeStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 상태 값입니다.");
        }
        List<Challenge> challenges = challengeService.getChallengeByStatusAndUserId(userId, challengeStatus);
        return challenges.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("챌린지가 없습니다.") :
                ResponseEntity.ok().body(challenges);
    }
    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getChallengeDetail(@PathVariable("challengeId") long id) {
        Challenge challenges = challengeService.getChallengeDetail(id);
        return challenges != null ?
                ResponseEntity.ok().body(challenges):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 챌린지가 없습니다.");
    }
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<String> removeChallenge(@PathVariable("challengeId") long id) {
        boolean isRemoved = challengeService.removeChallenge(id);
        return isRemoved == true ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제할 챌린지가 없습니다.");
    }
}