package com.him.fpjt.him_backend.user.dto;

import com.him.fpjt.him_backend.user.domain.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private long id;
    private String nickname;
    private String email;
    private String profileImg;
    private Tier tier;
    private long exp;
}