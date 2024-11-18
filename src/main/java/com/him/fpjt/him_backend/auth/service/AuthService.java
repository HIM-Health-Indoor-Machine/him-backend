package com.him.fpjt.him_backend.auth.service;

import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.dto.SignupDto;
import com.him.fpjt.him_backend.auth.dto.VerificationCodeDto;
import com.him.fpjt.him_backend.user.domain.User;

import java.time.LocalDateTime;

public interface AuthService {
     boolean checkDuplicatedNickname(String nickname);
     void sendVerificationCode(VerificationCodeDto emailDto);
     void verifyVerificationCode(VerificationCodeDto emailDto);
     void removeExpiredVerificationCode();
     void registerUser(SignupDto signupDto);
     void saveRefreshToken(String refreshToken, String userEmail, LocalDateTime expiryDate);
     RefreshToken findRefreshTokenByEmail(String email);
     void deleteRefreshTokenByEmail(String email);
     User findUserByEmail(String email);
}
