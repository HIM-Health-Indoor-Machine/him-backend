package com.him.fpjt.him_backend.user.controller;

import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import com.him.fpjt.him_backend.user.dto.UserModifyDto;
import com.him.fpjt.him_backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") long userId) {
        UserInfoDto userInfoDto = userService.getUserById(userId);
        return ResponseEntity.ok().body(userInfoDto);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> modifyUserInfo(@PathVariable("userId") long userId,
                                    @RequestBody UserModifyDto userModifyDto) {
        userService.modifyUserInfo(userId, userModifyDto);
        return ResponseEntity.ok().build();
    }
}