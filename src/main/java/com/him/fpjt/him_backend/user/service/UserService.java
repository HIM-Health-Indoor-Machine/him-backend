package com.him.fpjt.him_backend.user.service;

import java.util.List;

public interface UserService {

    void modifyUserExp(long userId, long expPoints) throws Exception;
    List<Long> getAllUserIds();
}
