package com.him.fpjt.him_backend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GameDto {
    private long gameId;

    // Lombok의 @Getter, @Setter 어노테이션은 Java 객체 필드에 대해 getter와 setter 메서드를 자동으로 생성
    // 하지만, boolean 타입의 필드의 경우, getter 메서드의 이름이 Jackson의 기본 작명 규칙과 충돌할 수 있다.
    // 예를 들어,
    // Lombok -> isIsAchieved() 라는 getter 메서드를 생성
    // Jackson -> isAchieved() 라는 메서드를 기대
    // => 올바른 역직렬화 불가
    // => @JsonProperty("isAchieved")을 통해 프론트단에서 Json으로 넘어오는 데이터를 isAchieved로 받겠다고 선언
    @JsonProperty("isAchieved")
    private boolean isAchieved;
}
