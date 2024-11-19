package com.him.fpjt.him_backend.auth;


import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.service.AuthService;
import com.him.fpjt.him_backend.common.filter.JwtRequestFilter;
import com.him.fpjt.him_backend.common.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    private JwtRequestFilter jwtRequestFilter;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        userDetailsService = mock(UserDetailsService.class);
        authService = mock(AuthService.class);
        jwtRequestFilter = new JwtRequestFilter(jwtUtil, userDetailsService, authService);
    }

    @Test
    void testExpiredAccessTokenAndRefreshTokenUsage() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        String email = "test@example.com";
        String expiredAccessToken = "expiredAccessToken";
        String validRefreshToken = "validRefreshToken";
        String newAccessToken = "newAccessToken";

        request.addHeader("Authorization", "Bearer " + expiredAccessToken);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtil.extractEmail(expiredAccessToken)).thenReturn(email);
        when(jwtUtil.isTokenExpired(expiredAccessToken)).thenReturn(true);
        when(authService.findRefreshTokenByEmail(email)).thenReturn(new RefreshToken(1L, validRefreshToken, email, LocalDateTime.now().plusDays(1)));
        when(jwtUtil.generateToken(email)).thenReturn(newAccessToken);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(1)).generateToken(email);
        assert Objects.equals(response.getHeader("New-Access-Token"), newAccessToken);
    }
}

