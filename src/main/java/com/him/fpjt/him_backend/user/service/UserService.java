package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import java.util.List;

public interface UserService {

    void modifyUserExp(long userId, long expPoints) throws Exception;
    List<Long> getAllUserIds();
    UserInfoDto getUserById(long id);
}
