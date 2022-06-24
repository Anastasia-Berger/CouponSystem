package com.jb.CouponSystem.config;

import com.jb.CouponSystem.login.UserData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class TokenConfig {

    @Bean
    public Map<String, UserData> map(){
        return new HashMap<>();
    }
}