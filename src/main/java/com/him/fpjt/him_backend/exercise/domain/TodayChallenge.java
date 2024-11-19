package com.him.fpjt.him_backend.exercise.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodayChallenge {
    private long id;
    private long cnt;
    private long challengeId;
    private LocalDate date;
    private boolean isAchieved;

    public TodayChallenge(long cnt, long challengeId, LocalDate date) {
        this.cnt = cnt;
        this.challengeId = challengeId;
        this.date = date;
    }

    public TodayChallenge(long cnt, long challengeId, LocalDate date, boolean isAchieved) {
        this.cnt = cnt;
        this.challengeId = challengeId;
        this.date = date;
        this.isAchieved = isAchieved;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }
}