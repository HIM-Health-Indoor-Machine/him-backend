package com.him.fpjt.him_backend.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final Key secretKey;
    private final long jwtExpirationMs;
    private final Key refreshSecretKey;
    private final long refreshExpirationMs;

    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   @Value("${jwt.expiration}") long jwtExpirationMs,
                   @Value("${refresh.secret}") String refreshSecretKey,
                   @Value("${refresh.expiration}") long refreshExpirationMs) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] refreshKeyBytes = refreshSecretKey.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtExpirationMs = jwtExpirationMs;
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshKeyBytes);
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateToken(String email, Long userId) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setHeaderParam("type", "access")
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email, Long userId) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setHeaderParam("type", "refresh")
                .setSubject(email)
                .claim("userId", userId)
                .setExpiration(new Date(currentTimeMillis + refreshExpirationMs))
                .signWith(refreshSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        try {
            return extractClaim(token, claims -> claims.get("userId", Long.class));
        } catch (ExpiredJwtException e) {
            return e.getClaims().get("userId", Long.class);
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration();
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(1)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
