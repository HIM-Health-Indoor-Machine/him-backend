package com.him.fpjt.him_backend.exercise.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Challenge {
    private long id;
    private ChallengeStatus status;
    private ExerciseType type;
    private LocalDate startDt;
    private LocalDate endDt;
    private long goalCnt;
    private int achievedCnt;
    private long userId;

    public Challenge(ChallengeStatus status, ExerciseType type,
            LocalDate startDt, LocalDate endDt,
            long goalCnt, long userId) {
        this.status = status;
        this.type = type;
        this.startDt = startDt;
        this.endDt = endDt;
        this.goalCnt = goalCnt;
        this.achievedCnt = 0;
        this.userId = userId;
    }
}