package com.him.fpjt.him_backend.auth.dao;

import com.him.fpjt.him_backend.auth.domain.RefreshToken;

public interface RefreshTokenDao {
    void saveRefreshToken(RefreshToken refreshToken);
    RefreshToken findRefreshTokenByEmail(String email);
    void deleteByUserEmail(String email);
}
