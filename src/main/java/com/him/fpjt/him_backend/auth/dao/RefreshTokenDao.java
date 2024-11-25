package com.him.fpjt.him_backend.auth.dao;

import com.him.fpjt.him_backend.auth.domain.RefreshToken;
import com.him.fpjt.him_backend.auth.dto.LogoutDto;

public interface RefreshTokenDao {
    void saveRefreshToken(RefreshToken refreshToken);
    RefreshToken findRefreshTokenByEmail(String email);
    int deleteByUserEmail(String email);
}
