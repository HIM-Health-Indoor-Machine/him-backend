package com.him.fpjt.him_backend.exercise.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    private long id;
    private String title;
    private ChallengeStatus status;
    private ExerciseType type;
    private LocalDate startDt;
    private LocalDate endDt;
    private long goalCnt;
    private int achievedCnt;
    private long userId;

    public Challenge(String title, ChallengeStatus status, ExerciseType type,
            LocalDate startDt, LocalDate endDt,
            long goalCnt, long userId) {
        this.title = title;
        this.status = status;
        this.type = type;
        this.startDt = startDt;
        this.endDt = endDt;
        this.goalCnt = goalCnt;
        this.achievedCnt = 0;
        this.userId = userId;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateType(ExerciseType type) {
        this.type = type;
    }

    public void updateStartDt(LocalDate startDt) {
        this.startDt = startDt;
    }

    public void updateEndDt(LocalDate endDt) {
        this.endDt = endDt;
    }

    public void updateGoalCnt(long goalCnt) {
        this.goalCnt = goalCnt;
    }
}