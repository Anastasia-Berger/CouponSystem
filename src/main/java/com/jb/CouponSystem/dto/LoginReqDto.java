package com.jb.CouponSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginReqDto {

    private String email;
    private String password;


}