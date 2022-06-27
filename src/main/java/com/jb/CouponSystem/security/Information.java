package com.jb.CouponSystem.security;

import com.jb.CouponSystem.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Information {

    private int id;
    private String email;
    private ClientType clientType;
    private LocalDateTime time = LocalDateTime.now();

    public Information(int id, String email, ClientType clientType) {
        this.id = id;
        this.email = email;
        this.clientType = clientType;
    }

    public Information(String email, ClientType clientType) {
        this.email = email;
        this.clientType = clientType;
    }
}
