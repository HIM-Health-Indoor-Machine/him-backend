package com.him.fpjt.him_backend.auth.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class VerificationCode {
    private long id;
    private String email;
    private String code;
    private LocalDateTime issuedAt;

    public VerificationCode(String email, String code, LocalDateTime issuedAt) {
        this.email = email;
        this.code = code;
        this.issuedAt = issuedAt;
    }
}