package com.him.fpjt.him_backend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class TodayChallengeDto {
    private long cnt = 0;
    private long challengeId;
    private LocalDate date = LocalDate.now();
}