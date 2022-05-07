package com.jb.CouponSystem.exeptions;

import java.util.function.Supplier;

public class CouponSystemException extends Exception {

    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getDescription());
    }
}
