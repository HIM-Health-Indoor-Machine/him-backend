package com.him.fpjt.him_backend.exercise.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class TodayChallenge {
    private long id;
    private long cnt;
    private long challengeId;
    private LocalDate date;

    public TodayChallenge(long cnt, long challengeId, LocalDate date) {
        this.cnt = cnt;
        this.challengeId = challengeId;
        this.date = date;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }
}