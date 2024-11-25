package com.him.fpjt.him_backend.auth.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.domain.Tier;
import com.him.fpjt.him_backend.user.domain.User;
import com.him.fpjt.him_backend.auth.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = mock(UserDao.class);
        userDetailsService = new UserDetailsServiceImpl(userDao);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        String email = "test@example.com";
        User mockUser = new User(1L, "nickname", email, "encodedPassword", "profileImg", Tier.IRON, 0);

        when(userDao.selectUserByEmail(email)).thenReturn(mockUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        assertNotNull(userDetails, "UserDetails가 null입니다.");
        assertEquals(email, userDetails.getUsername(), "이메일이 일치하지 않습니다.");
        assertEquals("encodedPassword", userDetails.getPassword(), "비밀번호가 일치하지 않습니다.");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userDao.selectUserByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email),
                "존재하지 않는 사용자에 대해 예외가 발생하지 않았습니다.");
    }
}
