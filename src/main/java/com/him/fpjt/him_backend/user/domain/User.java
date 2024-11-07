package com.him.fpjt.him_backend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class User {
    private long id;
    private String nickname;
    private String email;
    private String password;
    private String profileImg;
    private Tier tier = Tier.IRON;
    private long exp = 0;

    public User() {
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
