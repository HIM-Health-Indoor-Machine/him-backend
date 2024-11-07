package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.domain.Tier;
import com.him.fpjt.him_backend.user.domain.User;
import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import com.him.fpjt.him_backend.user.dto.UserModifyDto;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void modifyUserExp_success() {
        long userId = 1L;
        long expPoints = 20L;

        when(userDao.updateUserExp(userId, expPoints)).thenReturn(1);

        assertDoesNotThrow(() -> userService.modifyUserExp(userId, expPoints));
        verify(userDao, times(1)).updateUserExp(userId, expPoints);
    }

    @Test
    void modifyUserExp_failure() {
        long userId = 1L;
        long expPoints = 20L;

        when(userDao.updateUserExp(userId, expPoints)).thenReturn(0);

        Exception exception = assertThrows(Exception.class, () -> userService.modifyUserExp(userId, expPoints));
        verify(userDao, times(1)).updateUserExp(userId, expPoints);
    }

    @Test
    void modifyUserExp_noEffectOnInvalidUserId() {
        long invalidUserId = -1L;
        long expPoints = 10L;

        when(userDao.updateUserExp(invalidUserId, expPoints)).thenReturn(0);

        Exception exception = assertThrows(Exception.class, () -> userService.modifyUserExp(invalidUserId, expPoints));
        verify(userDao, times(1)).updateUserExp(invalidUserId, expPoints);
    }

    @Test
    void modifyUserExp_zeroExperiencePoints() {
        long userId = 1L;
        long expPoints = 0;

        // Given: updateUserExp가 성공적으로 업데이트 된다고 가정
        when(userDao.updateUserExp(userId, expPoints)).thenReturn(1);

        // When & Then
        assertDoesNotThrow(() -> userService.modifyUserExp(userId, expPoints));
        verify(userDao, times(1)).updateUserExp(userId, expPoints);
    }

    @Test
    @DisplayName("사용자 조회 시 사용자 정보를 반환한다.")
    void getUserById_success() {
        long userId = 1L;
        User mockUser = new User(userId, "ollie", "email@example.com", "password","ollie.jpg", Tier.IRON, 2000);
        when(userDao.selectUserById(userId)).thenReturn(mockUser);

        UserInfoDto userInfo = userService.getUserById(userId);

        assertNotNull(userInfo);
        assertEquals(userId, userInfo.getId());
        assertEquals("ollie", userInfo.getNickname());
        assertEquals("email@example.com", userInfo.getEmail());
    }
    @Test
    @DisplayName("사용자가 존재하지 않는 경우 NoSuchElementException가 발생한다.")
    void getUserById_noSuchElementException() {
        long userId = 1L;
        when(userDao.selectUserById(userId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(userId));
    }
}
