package com.him.fpjt.him_backend.user.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class Attendence {
    private long id;
    private LocalDate attendDt;
    private boolean isAttended;
    private long userId;

    public Attendence(long userId) {
        this.userId = userId;
        this.attendDt = LocalDate.now();
        this.isAttended = false; //게임 1개를 진행하면 true
    }
}
