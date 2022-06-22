package com.jb.CouponSystem.login;

import com.jb.CouponSystem.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private ClientService clientService;
    private long timeStamp;
}