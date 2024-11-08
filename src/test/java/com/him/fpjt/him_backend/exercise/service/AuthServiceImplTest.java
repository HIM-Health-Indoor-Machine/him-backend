package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.common.util.CodeGenerator;
import com.him.fpjt.him_backend.common.util.EmailSender;
import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.auth.dao.VerificationCodeDao;
import com.him.fpjt.him_backend.auth.domain.VerificationCode;
import com.him.fpjt.him_backend.auth.dto.VerificationCodeDto;
import com.him.fpjt.him_backend.auth.service.AuthServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthServiceImplTest {
    @Mock
    private UserDao userDao;
    @Mock
    private EmailSender emailSender;
    @Mock
    private CodeGenerator codeGenerator;
    @Mock
    private VerificationCodeDao verificationCodeDao;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("이미 존재하는 닉네임은 true를 반환합니다.")
    void checkDuplicatedNickname_duplicated() {
        String nickname = "ollie";
        when(userDao.existsDuplicatedNickname(nickname)).thenReturn(true);

        boolean isDuplicated = authService.checkDuplicatedNickname(nickname);

        assertTrue(isDuplicated);
    }

    @Test
    @DisplayName("이전에 없던 닉네임은 false를 반환합니다.")
    void checkDuplicatedNickname_unique() {
        String nickname = "ollie";
        when(userDao.existsDuplicatedNickname(nickname)).thenReturn(false);

        boolean isDuplicated = authService.checkDuplicatedNickname(nickname);

        assertFalse(isDuplicated);
    }

    @Test
    @DisplayName("인증 번호 요청 시, 이메일로 인증 번호가 발송된 후 인증 번호가 저장된다.")
    void sendVerificationCode() {
        when(codeGenerator.generateCode()).thenReturn("123456");
        doNothing().when(emailSender).sendVerificationCode(anyString(), anyString(), anyString());
        when(verificationCodeDao.insertVerificationCode(any(VerificationCode.class))).thenReturn(1);
        VerificationCodeDto verificationCodeDto = new VerificationCodeDto();
        verificationCodeDto.setEmail("ssafy@ssafy.com");
        authService.sendVerificationCode(verificationCodeDto);

        verify(codeGenerator).generateCode();
        verify(emailSender).buildTextForVerificationCode("123456");
        verify(verificationCodeDao).insertVerificationCode(any(VerificationCode.class));
    }
}