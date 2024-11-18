package com.him.fpjt.him_backend.auth;

import com.him.fpjt.him_backend.auth.dao.RefreshTokenDao;
import com.him.fpjt.him_backend.auth.dao.VerificationCodeDao;
import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.dto.SignupDto;
import com.him.fpjt.him_backend.auth.service.AuthServiceImpl;
import com.him.fpjt.him_backend.common.util.CodeGenerator;
import com.him.fpjt.him_backend.common.util.EmailSender;
import com.him.fpjt.him_backend.common.util.JwtUtil;
import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private AuthServiceImpl authService;
    private UserDao userDao;
    private RefreshTokenDao refreshTokenDao;
    private EmailSender emailSender;
    private CodeGenerator codeGenerator;
    private VerificationCodeDao verificationCodeDao;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        refreshTokenDao = mock(RefreshTokenDao.class);
        emailSender = mock(EmailSender.class);
        codeGenerator = mock(CodeGenerator.class);
        verificationCodeDao = mock(VerificationCodeDao.class);
        passwordEncoder = mock(PasswordEncoder.class);

        authService = new AuthServiceImpl(userDao, refreshTokenDao, emailSender, codeGenerator, verificationCodeDao, passwordEncoder);
    }

    @Test
    void testRegisterUser_Success() {
        SignupDto signupDto = new SignupDto("testUser", "test@example.com", "Password123!");

        when(userDao.existsByEmail(signupDto.getEmail())).thenReturn(false);
        when(userDao.existsDuplicatedNickname(signupDto.getNickname())).thenReturn(false);
        when(passwordEncoder.encode(signupDto.getPassword())).thenReturn("encodedPassword");

        authService.registerUser(signupDto);

        verify(userDao, times(1)).saveUser(any(User.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        SignupDto signupDto = new SignupDto("testUser", "test@example.com", "Password123!");

        when(userDao.existsByEmail(signupDto.getEmail())).thenReturn(true);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            authService.registerUser(signupDto);
        });

        assertEquals("이미 존재하는 이메일입니다.", thrown.getMessage());
        verify(userDao, never()).saveUser(any(User.class));
    }

    @Test
    void testSaveRefreshToken() {
        String email = "test@example.com";
        String token = "refreshToken";
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);

        authService.saveRefreshToken(token, email, expiryDate);

        verify(refreshTokenDao, times(1)).saveRefreshToken(any(RefreshToken.class));
    }

    @Test
    void testFindRefreshTokenByEmail() {
        String email = "test@example.com";
        RefreshToken refreshToken = new RefreshToken(1L, "refreshToken", email, LocalDateTime.now().plusDays(1));

        when(refreshTokenDao.findRefreshTokenByEmail(email)).thenReturn(refreshToken);

        RefreshToken result = authService.findRefreshTokenByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(refreshTokenDao, times(1)).findRefreshTokenByEmail(email);
    }

    @Test
    void testDeleteRefreshTokenByEmail() {
        String email = "test@example.com";

        authService.deleteRefreshTokenByEmail(email);

        verify(refreshTokenDao, times(1)).deleteByUserEmail(email);
    }
}

