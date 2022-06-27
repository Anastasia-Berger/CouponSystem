package com.jb.CouponSystem.exeptions;

import org.springframework.http.HttpStatus;

public enum ErrMsg {

    // throw new CouponSystemException(ErrMsg.XXXXXXXX);

    ID_NOT_EXIST("ID not found", HttpStatus.NOT_FOUND),
    ID_ALREADY_EXIST("ID already exist", HttpStatus.CONFLICT),
    COMPANY_ALREADY_EXIST("Company already exist", HttpStatus.CONFLICT),
    COMPANY_DOESNT_EXIST("Company doesnt exist", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXIST("Email already exist", HttpStatus.CONFLICT),
    ILLEGAL_ACTION_EXCEPTION("Illegal Action", HttpStatus.FORBIDDEN),
    ZERO_VALUE("The value cannot be less or equal to zero", HttpStatus.NOT_FOUND),
    INCORRECT_LOGIN("Dear Customer, you've entered incorrect username or password", HttpStatus.UNAUTHORIZED),
    OUT_OF_AMOUNT("Coupon is out of stock", HttpStatus.NOT_FOUND),
    EXPIRED("Sorry, this coupon already expired.", HttpStatus.NOT_FOUND),
    ALREADY_PURCHASED("Customer already purchased this coupon", HttpStatus.CONFLICT),
    TOKEN_NOT_EXIST("Token not recognised", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_EVENT("Unauthorized event", HttpStatus.UNAUTHORIZED);

    private String description;
    private HttpStatus status;

    ErrMsg(String description, HttpStatus notFound) {
        this.description = description;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
