package com.him.fpjt.him_backend.auth.service;

import com.him.fpjt.him_backend.auth.dao.RefreshTokenDao;
import com.him.fpjt.him_backend.auth.dao.VerificationCodeDao;
import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.dto.*;
import com.him.fpjt.him_backend.common.exception.EmailAlreadyExistsException;
import com.him.fpjt.him_backend.common.exception.NicknameAlreadyExistsException;
import com.him.fpjt.him_backend.common.constants.EmailTemplates;
import com.him.fpjt.him_backend.common.util.CodeGenerator;
import com.him.fpjt.him_backend.common.util.EmailSender;
import com.him.fpjt.him_backend.common.util.JwtUtil;
import com.him.fpjt.him_backend.user.dao.AttendanceDao;
import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.auth.domain.VerificationCode;
import com.him.fpjt.him_backend.user.domain.Attendance;
import org.springframework.security.authentication.AuthenticationManager;
import java.time.Duration;
import java.time.LocalDateTime;

import com.him.fpjt.him_backend.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final AttendanceDao attendanceDao;
    private final RefreshTokenDao refreshTokenDao;
    private final EmailSender emailSender;
    private final CodeGenerator codeGenerator;
    private final VerificationCodeDao verificationCodeDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserDao userDao, AttendanceDao attendanceDao,
            RefreshTokenDao refreshTokenDao, EmailSender emailSender, CodeGenerator codeGenerator,
            VerificationCodeDao verificationCodeDao, PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.attendanceDao = attendanceDao;
        this.refreshTokenDao = refreshTokenDao;
        this.emailSender = emailSender;
        this.codeGenerator = codeGenerator;
        this.verificationCodeDao = verificationCodeDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userDao.selectUserByEmail(authenticationRequest.getEmail());
        Long userId = user.getId();

        String accessToken = jwtUtil.generateToken(authenticationRequest.getEmail(), userId);
        String refreshToken = jwtUtil.generateRefreshToken(authenticationRequest.getEmail(), userId);

        saveRefreshToken(refreshToken, authenticationRequest.getEmail(), LocalDateTime.now().plusDays(30));

        return new AuthenticationResponse(accessToken, authenticationRequest.getEmail(), userId, "로그인 성공");
    }

    @Transactional
    @Override
    public void signupUser(SignupDto signupDto) {
        if (userDao.existsByEmail(signupDto.getEmail())) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        if (userDao.existsDuplicatedNickname(signupDto.getNickname())) {
            throw new NicknameAlreadyExistsException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());

        User newUser = new User(signupDto.getNickname(), signupDto.getEmail(), encodedPassword);

        userDao.saveUser(newUser);
        attendanceDao.insertAttendance(new Attendance(userDao.selectUserByEmail(signupDto.getEmail()).getId()));
    }

    @Transactional
    @Override
    public void saveRefreshToken(String refreshToken, String userEmail, LocalDateTime expiryDate) {
        RefreshToken tokenEntity = new RefreshToken(refreshToken, userEmail, expiryDate);
        refreshTokenDao.saveRefreshToken(tokenEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public RefreshToken findRefreshTokenByEmail(String email) {
        RefreshToken refreshToken = refreshTokenDao.findRefreshTokenByEmail(email);
        if (refreshToken == null) {
            throw new IllegalArgumentException("해당 이메일에 대한 Refresh Token이 존재하지 않습니다.");
        }
        return refreshToken;
    }

    @Transactional
    @Override
    public void deleteRefreshTokenByEmail(String email) {
        int rowsAffected = refreshTokenDao.deleteByUserEmail(email);
        if (rowsAffected == 0) {
            throw new IllegalStateException("해당 이메일에 대한 Refresh Token이 존재하지 않습니다.");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userDao.selectUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("해당 이메일을 가진 사용자가 없습니다.");
        }
        return user;
    }

    @Transactional
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

    @Transactional
    void saveVerificationCode(VerificationCodeDto verificationCodeDto, String code) {
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
    @Override
    @Transactional
    @Scheduled(cron = "0 0 2 * * ?")
    public void removeExpiredVerificationCode(){
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(15);
        verificationCodeDao.deleteExpiredCodes(expiredTime);
    }
}
