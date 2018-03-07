package com.nonobank.platformuser.component;

/**
 * Created by tangrubei on 2018/3/1.
 */
public class MyUserException extends RuntimeException {
    private static final long serialVersionUID = 2495559366495945814L;

    private int errorCode;

    private String errorMessage;

    public MyUserException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
