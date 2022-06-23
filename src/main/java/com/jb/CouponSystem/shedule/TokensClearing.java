package com.jb.CouponSystem.shedule;

import com.jb.CouponSystem.login.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class TokensClearing {

    @Autowired
    private TokenManager tokensManager;

    @Scheduled(fixedRate = (1000 * 60 * 30))
    public void deleteExpiredTokenOver30Min() {
        tokensManager.deleteExpiredToken();
    }
}
