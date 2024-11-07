package com.him.fpjt.him_backend.user.dao;

import com.him.fpjt.him_backend.user.domain.User;
import java.util.List;

public interface UserDao {

    int updateUserExp(long id, long expPoints);
    List<Long> selectAllUserIds();
    User selectUserById(long id);
}
