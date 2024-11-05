package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import java.util.List;
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
    public void modifyUserExp(long userId, long expPoints) {
        int updateResult = userDao.updateUserExp(userId, expPoints);
        if (updateResult == 0) {
            throw new UnsupportedOperationException("사용자 경험치 업데이트에 실패했습니다.");
        }
    }

    @Override
    public List<Long> getAllUserIds() {
        return userDao.selectAllUserIds();
    }
}
