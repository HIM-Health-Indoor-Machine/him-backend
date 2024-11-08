package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthServiceImplTest {
    @Mock
    private UserDao userDao;

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

}