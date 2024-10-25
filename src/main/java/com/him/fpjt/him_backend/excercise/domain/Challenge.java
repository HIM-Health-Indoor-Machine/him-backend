package com.him.fpjt.him_backend.excercise.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Challenge {
    private long id;
    private ChallengeStatus status;
    private ExcerciseType type;
    private LocalDate startDt;
    private LocalDate endDt;
    private long goalCnt;
    private int achievedCnt;
    private long userId;

    public Challenge(ChallengeStatus status, ExcerciseType type, LocalDate startDt, LocalDate endDt,
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