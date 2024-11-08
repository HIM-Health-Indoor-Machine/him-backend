package com.him.fpjt.him_backend.auth.service;

import com.him.fpjt.him_backend.auth.dao.VerificationCodeDao;
import com.him.fpjt.him_backend.common.constants.EmailTemplates;
import com.him.fpjt.him_backend.common.util.CodeGenerator;
import com.him.fpjt.him_backend.common.util.EmailSender;
import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.auth.domain.VerificationCode;
import com.him.fpjt.him_backend.auth.dto.VerificationCodeDto;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final EmailSender emailSender;
    private final CodeGenerator codeGenerator;
    private final VerificationCodeDao verificationCodeDao;

    public AuthServiceImpl(UserDao userDao, EmailSender emailSender, CodeGenerator codeGenerator,
            VerificationCodeDao verificationCodeDao) {
        this.userDao = userDao;
        this.emailSender = emailSender;
        this.codeGenerator = codeGenerator;
        this.verificationCodeDao = verificationCodeDao;
    }

    @Override
    public boolean checkDuplicatedNickname(String nickname) {
        return userDao.existsDuplicatedNickname(nickname);
    }
    @Transactional
    @Override
    public void sendVerificationCode(VerificationCodeDto verificationCodeDto) {
        String code = codeGenerator.generateCode();
        String text = emailSender.buildTextForVerificationCode(code);
        emailSender.sendVerificationCode(verificationCodeDto.getEmail(), EmailTemplates.VERIFICATION_CODE_SUBJECT, text);
        saveVerificationCode(verificationCodeDto, code);
    }

    private void saveVerificationCode(VerificationCodeDto verificationCodeDto, String code) {
        VerificationCode verificationCode = new VerificationCode(verificationCodeDto.getEmail(), code, LocalDateTime.now());
        int result = verificationCodeDao.insertVerificationCode(verificationCode);
        if (result == 0) {
            throw new IllegalStateException("인증 코드 저장에 실패했습니다.");
        }
    }

    @Transactional
    @Override
    public void verifyVerificationCode(VerificationCodeDto verificationCodeDto) {
        VerificationCode verificationCode = verificationCodeDao.selectVerificationCodeByEmail(verificationCodeDto.getEmail());
        if (verificationCode == null || verificationCode.getIssuedAt() == null ||
                !verificationCode.getCode().equals(verificationCodeDto.getCode()) ||
                Duration.between(verificationCode.getIssuedAt(), LocalDateTime.now()).toMinutes() > 15) {
            throw new IllegalArgumentException("인증에 실패했습니다.");
        }
    }
}
