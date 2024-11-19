package com.him.fpjt.him_backend.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    private long id;
    private String token;
    private String email;
    private LocalDateTime expiryDate;

    public RefreshToken(String token, String email, LocalDateTime expiryDate){
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }
}
