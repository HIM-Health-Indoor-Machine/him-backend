package com.him.fpjt.him_backend.common.exception.dto;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ExceptionDto {
    private HttpStatus status;
    private String msg;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ExceptionDto(HttpStatus status, String msg, LocalDateTime timestamp) {
        this.status = status;
        this.msg = msg;
        this.timestamp = timestamp;
    }
}
