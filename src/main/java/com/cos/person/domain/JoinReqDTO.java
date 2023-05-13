package com.cos.person.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JoinReqDTO {
    @NotNull(message = "이름이 없습니다.")
    @NotBlank(message = "이름을 입력하세요.")
    @Size(max = 20, message = "글자수를 초과하였습니다.")
    private String username;

    @NotNull(message = "비밀번호가 없습니다.")
    private String password;

    @NotNull(message = "번호가 없습니다.")
    private String phone;
}
