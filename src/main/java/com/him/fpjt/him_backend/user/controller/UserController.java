package com.him.fpjt.him_backend.user.controller;

import com.him.fpjt.him_backend.auth.service.AuthService;
import com.him.fpjt.him_backend.user.dto.UserInfoDto;
import com.him.fpjt.him_backend.user.dto.UserModifyDto;
import com.him.fpjt.him_backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") long userId, Authentication authentication) {
        String currentEmail = authentication.getName();
        System.out.println("UserController: " + currentEmail);
        long currentUserId = authService.findUserByEmail(currentEmail).getId();

        UserInfoDto userInfoDto = userService.getUserById(userId, currentUserId);
        return ResponseEntity.ok().body(userInfoDto);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> modifyUserInfo(@PathVariable("userId") long userId,
                                            @RequestBody UserModifyDto userModifyDto,
                                            Authentication authentication) {
        String currentEmail = authentication.getName();
        long currentUserId = authService.findUserByEmail(currentEmail).getId();

        userService.modifyUserInfo(userId, currentUserId, userModifyDto);
        return ResponseEntity.ok().build();
    }
}