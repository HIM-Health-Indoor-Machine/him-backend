package com.him.fpjt.him_backend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.domain.ExerciseType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallengeDto {
    private ExerciseType type;
    private LocalDate startDt;
    private LocalDate endDt;
    private long goalCnt;
}
