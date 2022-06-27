package com.jb.CouponSystem.dto;

import com.jb.CouponSystem.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResDto {

    private String email;
    private UUID token;
    private ClientType clientType;
}
