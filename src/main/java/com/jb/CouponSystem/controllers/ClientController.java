package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.login.LoginManager;
import com.jb.CouponSystem.login.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public abstract class ClientController {

    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private LoginManager loginManager;


    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            return new ResponseEntity<>(loginManager.login(email, password, ClientType.ADMINISTRATOR), HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(ErrMsg.UNAUTHORIZED_EVENT, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("authorization") String token) {
        tokenManager.deleteToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
