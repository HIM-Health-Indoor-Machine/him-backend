package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;

    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean checkDuplicatedNickname(String nickname) {
        return userDao.existsDuplicatedNickname(nickname);
    }
}
