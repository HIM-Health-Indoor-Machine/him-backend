package com.him.fpjt.him_backend.auth.service;

import com.him.fpjt.him_backend.auth.dao.RefreshTokenDao;
import com.him.fpjt.him_backend.auth.dao.VerificationCodeDao;
import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.dto.AuthenticationRequest;
import com.him.fpjt.him_backend.auth.dto.SignupDto;
import com.him.fpjt.him_backend.auth.dto.AuthenticationResponse;
import com.him.fpjt.him_backend.auth.service.AuthServiceImpl;
import com.him.fpjt.him_backend.common.exception.EmailAlreadyExistsException;
import com.him.fpjt.him_backend.common.util.CodeGenerator;
import com.him.fpjt.him_backend.common.util.EmailSender;
import com.him.fpjt.him_backend.common.util.JwtUtil;
import com.him.fpjt.him_backend.user.dao.AttendanceDao;
import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.domain.Attendance;
import com.him.fpjt.him_backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private AuthServiceImpl authService;
    private UserDao userDao;
    private AttendanceDao attendanceDao;
    private RefreshTokenDao refreshTokenDao;
    private VerificationCodeDao verificationCodeDao;
    private EmailSender emailSender;
    private CodeGenerator codeGenerator;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        attendanceDao = mock(AttendanceDao.class);
        refreshTokenDao = mock(RefreshTokenDao.class);
        verificationCodeDao = mock(VerificationCodeDao.class);
        emailSender = mock(EmailSender.class);
        codeGenerator = mock(CodeGenerator.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        authenticationManager = mock(AuthenticationManager.class);

        authService = new AuthServiceImpl(userDao, attendanceDao, refreshTokenDao, emailSender, codeGenerator,
                verificationCodeDao, passwordEncoder, jwtUtil, authenticationManager);
    }

    @Test
    void testLogin_Success() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken(request.getEmail(), 1L)).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(request.getEmail(), 1L)).thenReturn("refreshToken");
        when(userDao.selectUserByEmail(request.getEmail())).thenReturn(new User(1L, "testUser", "test@example.com", "password", null, null, 0L));

        AuthenticationResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(1L, response.getUserId());
        assertEquals("로그인 성공", response.getMessage());

        verify(refreshTokenDao, times(1)).saveRefreshToken(any(RefreshToken.class));
    }

    @Test
    void testSignupUser_Success() {
        SignupDto signupDto = new SignupDto("testUser", "test@example.com", "Password123!");

        when(userDao.existsByEmail(signupDto.getEmail())).thenReturn(false);
        when(userDao.existsDuplicatedNickname(signupDto.getNickname())).thenReturn(false);
        when(passwordEncoder.encode(signupDto.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User(1L, "testUser", "test@example.com", "encodedPassword", null, null, 0L);
        when(userDao.selectUserByEmail(signupDto.getEmail())).thenReturn(savedUser);

        authService.signupUser(signupDto);

        verify(userDao, times(1)).saveUser(any(User.class));
    }

    @Test
    void testSignupUser_EmailAlreadyExists() {
        SignupDto signupDto = new SignupDto("testUser", "test@example.com", "Password123!");

        when(userDao.existsByEmail(signupDto.getEmail())).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class,
                () -> authService.signupUser(signupDto));

        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
        verify(userDao, never()).saveUser(any(User.class));
    }

    @Test
    void testFindRefreshTokenByEmail() {
        String email = "test@example.com";
        RefreshToken refreshToken = new RefreshToken("refreshToken", email, LocalDateTime.now().plusDays(30));

        when(refreshTokenDao.findRefreshTokenByEmail(email)).thenReturn(refreshToken);

        RefreshToken result = authService.findRefreshTokenByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testDeleteRefreshTokenByEmail() {
        String email = "test@example.com";

        when(refreshTokenDao.deleteByUserEmail(email)).thenReturn(1);

        assertDoesNotThrow(() -> authService.deleteRefreshTokenByEmail(email));

        verify(refreshTokenDao, times(1)).deleteByUserEmail(email);
    }

    @Test
    void testFindUserByEmail_Success() {
        String email = "test@example.com";
        User user = new User(1L, "testUser", email, "password", null, null, 0L);

        when(userDao.selectUserByEmail(email)).thenReturn(user);

        User result = authService.findUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testFindUserByEmail_NotFound() {
        String email = "nonexistent@example.com";

        when(userDao.selectUserByEmail(email)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.findUserByEmail(email));

        assertEquals("해당 이메일을 가진 사용자가 없습니다.", exception.getMessage());
    }
}
