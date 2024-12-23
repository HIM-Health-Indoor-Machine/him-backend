package com.him.fpjt.him_backend.exercise.domain;

import java.time.LocalDate;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Game {
    private long id;
    private LocalDate date = LocalDate.now();
    private ExerciseType type;
    private DifficultyLevel difficultyLevel;
    private boolean isAchieved;
    private long userId;
    
    public Game(ExerciseType type, DifficultyLevel difficultyLevel, boolean isAchieved, long userId) {
        this.type = type;
        this.difficultyLevel = difficultyLevel;
        this.isAchieved = isAchieved;
        this.userId = userId;
        this.date = LocalDate.now();
    }
}
