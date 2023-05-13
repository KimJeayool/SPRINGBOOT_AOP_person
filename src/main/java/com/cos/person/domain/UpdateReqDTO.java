package com.cos.person.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateReqDTO {
    @NotNull(message = "비밀번호가 없습니다.")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotNull(message = "번호가 없습니다.")
    private String phone;
}
