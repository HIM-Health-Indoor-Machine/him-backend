package com.him.fpjt.him_backend.common.exception;

import com.him.fpjt.him_backend.common.exception.dto.ExceptionDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionDto> handleNoSuchElementException(NoSuchElementException e) {
        log.error("리소스 없음 : {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.NO_CONTENT, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("잘못된 인자 전달 : {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionDto> handleIllegalStateException(IllegalStateException e) {
        log.error("잘못된 상태 : {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        log.error("서버 오류 발생 : {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionDto> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("만료된 JWT 토큰: {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 다시 로그인해주세요.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDto> handleJwtException(io.jsonwebtoken.JwtException e) {
        log.error("JWT 처리 오류: {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.BAD_REQUEST, "JWT 처리 중 오류가 발생했습니다.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUsernameNotFoundException(org.springframework.security.core.userdetails.UsernameNotFoundException e) {
        log.error("사용자 정보 없음: {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationException(org.springframework.security.core.AuthenticationException e) {
        log.error("인증 오류: {}", e.getMessage(), e);
        final ExceptionDto dto = new ExceptionDto(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다. 다시 시도해주세요.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }

}