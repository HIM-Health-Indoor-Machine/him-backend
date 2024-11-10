package com.him.fpjt.him_backend.common.exception;

import com.him.fpjt.him_backend.common.exception.dto.ExceptionDto;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> handleConstraintViolationException(ConstraintViolationException e) {
        final ExceptionDto dto = new ExceptionDto(HttpStatus.BAD_REQUEST, "입력 값이 올바르지 않습니다.", LocalDateTime.now());
        log.error("입력 값 검증 오류 발생 : {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage));
        log.error("입력 값 검증 오류 발생 : {}", errors, e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.BAD_REQUEST,  "입력 값이 올바르지 않습니다.", LocalDateTime.now(), errors);
        return ResponseEntity.badRequest().body(dto);
    }
}