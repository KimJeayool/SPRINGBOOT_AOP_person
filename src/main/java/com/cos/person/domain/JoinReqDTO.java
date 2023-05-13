package com.cos.person.domain;

import lombok.Data;

@Data
public class JoinReqDTO {
    private String username;
    private String password;
    private String phone;
}
