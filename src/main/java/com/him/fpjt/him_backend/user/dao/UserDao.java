package com.him.fpjt.him_backend.user.dao;

import java.util.List;

public interface UserDao {

    int updateUserExp(long userId, long expPoints);
    List<Long> selectAllUserIds();
}
    boolean existsDuplicatedNickname(String nickname);
