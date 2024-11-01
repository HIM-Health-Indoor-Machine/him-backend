package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void updateUserExp(long userId, long expPoints) throws Exception {
        int updateResult = userDao.updateUserExp(userId, expPoints);
        if (updateResult == 0) {
            throw new Exception("사용자 경험치 업데이트에 실패했습니다.");
        }
    }
}
