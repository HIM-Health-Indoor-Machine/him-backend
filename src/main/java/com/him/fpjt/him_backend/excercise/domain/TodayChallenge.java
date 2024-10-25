package com.him.fpjt.him_backend.excercise.domain;

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
