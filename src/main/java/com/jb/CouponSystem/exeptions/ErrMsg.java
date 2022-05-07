package com.jb.CouponSystem.exeptions;

public enum ErrMsg {

    // throw new CouponSystemException(ErrMsg.XXXXXXXX);

    ID_NOT_EXIST("ID not found"),
    ID_ALREADY_EXIST("ID already exist"),
    COMPANY_ALREADY_EXIST("Company already exist"),
    EMAIL_ALREADY_EXIST("Email already exist"),
    ILLEGAL_ACTION_EXCEPTION("Illegal Action"),
    ZERO_VALUE("The value cannot be less or equal to zero"),
    INCORRECT_LOGIN("Incorrect login details please try again"),
    OUT_OF_AMOUNT("Coupon is out of stock"),
    EXPIRED("Sorry, this coupon already expired."),
    ALREADY_PURCHASED("Customer already purchased this coupon");

    private String description;

    ErrMsg(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
