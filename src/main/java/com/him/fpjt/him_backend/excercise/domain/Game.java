package com.him.fpjt.him_backend.excercise.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Game {
    private long id;
    private LocalDate date;
    private ExcerciseType type;
    private DifficultyLevel difficultyLevel;
    private boolean isAchived;
    private long userId;

    public Game(ExcerciseType type, DifficultyLevel difficultyLevel, boolean isAchived,
            long userId) {
        this.type = type;
        this.difficultyLevel = difficultyLevel;
        this.isAchived = isAchived;
        this.userId = userId;
        this.date = LocalDate.now();
    }
}
