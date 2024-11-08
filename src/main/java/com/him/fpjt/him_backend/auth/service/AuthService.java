package com.him.fpjt.him_backend.auth.service;

import com.him.fpjt.him_backend.auth.dto.VerificationCodeDto;

public interface AuthService {
     boolean checkDuplicatedNickname(String nickname);
     void sendVerificationCode(VerificationCodeDto emailDto);
     void verifyVerificationCode(VerificationCodeDto emailDto);
     void removeExpiredVerificationCode();
}
