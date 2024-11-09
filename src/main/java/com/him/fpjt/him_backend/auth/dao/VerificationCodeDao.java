package com.him.fpjt.him_backend.auth.dao;

import com.him.fpjt.him_backend.auth.domain.VerificationCode;
import java.time.LocalDateTime;

public interface VerificationCodeDao {
    int insertVerificationCode(VerificationCode verificationCode);
    VerificationCode selectVerificationCodeByEmail(String email);
    int deleteExpiredCodes(LocalDateTime expiredTime);
}
