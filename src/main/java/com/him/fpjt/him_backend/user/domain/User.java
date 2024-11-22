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
    private Tier tier;
    private long exp;

    public User() {}

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImg = "/src/assets/images/character/character_CAT.png";
        this.tier = Tier.IRON;
        this.exp = 0L;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
