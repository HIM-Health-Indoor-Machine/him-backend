package com.him.fpjt.him_backend.common.filter;

import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.service.AuthService;
import com.him.fpjt.him_backend.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, @Lazy AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = extractJwtFromHeader(authorizationHeader);
        String email = jwt != null ? jwtUtil.extractEmail(jwt) : null;

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtUtil.isTokenExpired(jwt)) {
                handleExpiredToken(response, email, userDetails, request);
            } else if (jwtUtil.validateToken(jwt, email)) {
                setAuthentication(userDetails, request);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private void handleExpiredToken(HttpServletResponse response, String email, UserDetails userDetails, HttpServletRequest request) throws IOException {
        logger.info("Access Token이 만료되었습니다. 서버에서 Refresh Token을 확인합니다.");

        RefreshToken refreshToken = authService.findRefreshTokenByEmail(email);
        if (refreshToken != null && refreshToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            String newAccessToken = jwtUtil.generateToken(email);
            response.setHeader("New-Access-Token", newAccessToken);
            logger.info("새로운 Access Token이 발급되었습니다.");

            setAuthentication(userDetails, request);
        } else {
            logger.warn("Refresh Token이 만료되었거나 존재하지 않습니다.");
            authService.deleteRefreshTokenByEmail(email);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Refresh Token이 만료되었습니다. 다시 로그인해주세요.");
        }
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
