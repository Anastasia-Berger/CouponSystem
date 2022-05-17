package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.enums.ClientType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping("login")
    public UUID login(@Valid @RequestBody String email, String password, ClientType clientType) {

        return UUID.randomUUID();
    }
}
