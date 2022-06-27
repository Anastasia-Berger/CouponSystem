package com.jb.CouponSystem.models;

import com.jb.CouponSystem.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReq {

    private String email;
    private String password;
    private ClientType type;
}
