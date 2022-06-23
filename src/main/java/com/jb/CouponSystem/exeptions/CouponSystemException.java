package com.jb.CouponSystem.exeptions;

public class CouponSystemException extends Exception {

    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getDescription());
    }
}
