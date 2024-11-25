package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import com.him.fpjt.him_backend.user.dto.UserModifyDto;
import java.util.List;

public interface UserService {

    void modifyUserExp(long userId, long expPoints);
    List<Long> getAllUserIds();
    UserInfoDto getUserById(long userId, long currentUserId);
    void modifyUserInfo(long userId, long currentUserId, UserModifyDto userModifyDto);
}