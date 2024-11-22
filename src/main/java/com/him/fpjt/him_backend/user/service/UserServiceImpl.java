package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.dao.UserDao;
import com.him.fpjt.him_backend.user.domain.User;
import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import com.him.fpjt.him_backend.user.dto.UserModifyDto;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.access.AccessDeniedException;
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
            throw new IllegalStateException("사용자 경험치 업데이트에 실패했습니다.");
        }
    }

    @Override
    public List<Long> getAllUserIds() {
        return userDao.selectAllUserIds();
    }

    @Override
    public UserInfoDto getUserById(long userId, long currentUserId) {
        if (userId != currentUserId) {
            throw new AccessDeniedException("다른 사용자의 정보에 접근할 수 없습니다.");
        }
        User user = verifyUserExists(userId);
        return new UserInfoDto(user.getId(), user.getNickname(), user.getEmail(),
                user.getProfileImg(), user.getTier(), user.getExp());
    }
    private User verifyUserExists(long userId) {
        User user = userDao.selectUserById(userId);
        if (user == null) {
            throw new NoSuchElementException("없는 회원입니다.");
        }
        return user;
    }
    @Override
    @Transactional
    public void modifyUserInfo(long userId, long currentUserId, UserModifyDto userModifyDto) {
        if (userId != currentUserId) {
            throw new AccessDeniedException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        User user = verifyUserExists(userId);
        modifyUserFields(userModifyDto, user);
        int result = userDao.updateUserInfo(user);
        if (result == 0) {
            throw new IllegalStateException("회원 정보 수정을 실패하였습니다.");
        }
    }

    private static void modifyUserFields(UserModifyDto userModifyDto, User user) {
        user.updateNickname(userModifyDto.getNickname());
        user.updateProfileImg(userModifyDto.getProfileImg());
    }
}