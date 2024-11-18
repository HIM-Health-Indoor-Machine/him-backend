package com.him.fpjt.him_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    @NotBlank
    private String nickname;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$",
            message = "비밀번호는 8자 이상이어야 하며, 숫자, 특수 문자(!@#$%^&*) 및 영문자를 포함해야 합니다.")
    private String password;
}