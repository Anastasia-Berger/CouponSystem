package com.jb.CouponSystem.dto;

import com.jb.CouponSystem.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class RegisterResDto {

    private String email;
    private UUID token;
    private ClientType clientType;
}
