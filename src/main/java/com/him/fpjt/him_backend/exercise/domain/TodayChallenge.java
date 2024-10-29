package com.him.fpjt.him_backend.exercise.domain;

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
    private long userId;
}
