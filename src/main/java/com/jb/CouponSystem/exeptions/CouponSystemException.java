package com.jb.CouponSystem.exeptions;

public class CouponSystemException extends Exception {

    private ErrMsg errMsg;

    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getDescription());
    }

    public ErrMsg getErrMsg() {
        return errMsg;
    }

}
