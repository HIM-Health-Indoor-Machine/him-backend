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
}
