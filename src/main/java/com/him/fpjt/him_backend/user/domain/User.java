package com.him.fpjt.him_backend.user.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    private long id;
    private String nickname;
    private String email;
    private String profileImg;
    private Tier tier = Tier.IRON;
    private long exp = 0;

    public User() {
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void updateTier(Tier tier) {
        this.tier = tier;
    }

    public void updateExp(long exp) {
        this.exp = exp;
    }
}
