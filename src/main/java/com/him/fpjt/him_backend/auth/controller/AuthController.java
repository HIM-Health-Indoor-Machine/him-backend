package com.him.fpjt.him_backend.auth.controller;

import com.him.fpjt.him_backend.auth.dto.*;
import com.him.fpjt.him_backend.auth.service.AuthService;
import com.him.fpjt.him_backend.common.util.JwtUtil;
import com.him.fpjt.him_backend.user.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupDto signupDto) {
        authService.registerUser(signupDto);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("Attempting to authenticate: " + authenticationRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        String accessToken = jwtUtil.generateToken(authenticationRequest.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(authenticationRequest.getEmail());

        authService.saveRefreshToken(refreshToken, authenticationRequest.getEmail(), LocalDateTime.now().plusDays(30));

        User user = authService.findUserByEmail(authenticationRequest.getEmail());
        Long userId = user.getId();

        return ResponseEntity.ok(new AuthenticationResponse(accessToken, authenticationRequest.getEmail(), userId, "로그인 성공"));
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