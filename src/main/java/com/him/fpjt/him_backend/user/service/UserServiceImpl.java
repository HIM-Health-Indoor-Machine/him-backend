package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.domain.User;
import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import java.util.List;
import java.util.NoSuchElementException;
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
    public void modifyUserExp(long id, long expPoints) {
        int updateResult = userDao.updateUserExp(id, expPoints);
        if (updateResult == 0) {
            throw new UnsupportedOperationException("사용자 경험치 업데이트에 실패했습니다.");
        }
    }

    @Override
    public List<Long> getAllUserIds() {
        return userDao.selectAllUserIds();
    }

    @Override
    public UserInfoDto getUserById(long id) {
        User user = verifyUserExists(id);
        return new UserInfoDto(user.getId(), user.getNickname(), user.getEmail(),
                user.getProfileImg(), user.getTier(), user.getExp());
    }
    private User verifyUserExists(long id) {
        User user = userDao.selectUserById(id);
        if (user == null) {
            throw new NoSuchElementException("없는 회원입니다.");
        }
        return user;
    }
}