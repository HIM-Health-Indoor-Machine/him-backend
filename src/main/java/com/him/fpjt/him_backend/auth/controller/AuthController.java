package com.him.fpjt.him_backend.auth.controller;

import com.him.fpjt.him_backend.auth.dto.*;
import com.him.fpjt.him_backend.auth.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid @RequestBody SignupDto signupDto) {
        authService.signupUser(signupDto);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = authService.login(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String email) {
        authService.deleteRefreshTokenByEmail(email);
        return ResponseEntity.ok("로그아웃이 완료되었습니다. 모든 토큰이 삭제되었습니다.");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkDuplicateNickname(@NotBlank @RequestParam("nickname") String nickname) {
        boolean isDuplicated = authService.checkDuplicatedNickname(nickname);
        if (isDuplicated) {
            return ResponseEntity.ok().body("이미 사용 중인 닉네임입니다.");
        }
        return ResponseEntity.ok().body("사용 가능한 닉네임입니다.");
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@Valid @RequestBody VerificationCodeDto verificationCodeDto) {
        authService.sendVerificationCode(verificationCodeDto);
        return ResponseEntity.ok().body("인증코드가 발송되었습니다.");
    }
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyVerificationCode(@Valid @RequestBody VerificationCodeDto verificationCodeDto) {
        authService.verifyVerificationCode(verificationCodeDto);
        return ResponseEntity.ok().body("인증에 성공했습니다.");
    }

}