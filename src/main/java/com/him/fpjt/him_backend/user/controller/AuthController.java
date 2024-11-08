package com.him.fpjt.him_backend.user.controller;

import com.him.fpjt.him_backend.user.service.AuthService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkDuplicateNickname(@NotBlank @RequestParam("nickname") String nickname) {
        boolean isDuplicated = authService.checkDuplicatedNickname(nickname);
        if (isDuplicated) {
            return ResponseEntity.ok().body("이미 사용 중인 닉네임입니다.");
        }
        return ResponseEntity.ok().body("사용 가능한 닉네임입니다.");
    }
}
