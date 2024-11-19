package com.him.fpjt.him_backend.user.dao;

import com.him.fpjt.him_backend.user.domain.User;
import java.util.List;

public interface UserDao {
    int updateUserExp(long userId, long expPoints);
    List<Long> selectAllUserIds();
    User selectUserById(long userId);
    User selectUserByEmail(String email);
    int updateUserInfo(User user);
    boolean existsDuplicatedNickname(String nickname);
    void saveUser(User user);
    boolean existsByEmail(String email);
}
