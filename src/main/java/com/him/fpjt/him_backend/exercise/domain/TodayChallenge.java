package com.him.fpjt.him_backend.exercise.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TodayChallenge {
    private long id;
    private long cnt;
    private long challengeId;
    private long userId;
}
