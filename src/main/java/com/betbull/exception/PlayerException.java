package com.betbull.exception;

public class PlayerException extends Exception {

    private String message;
    private int errorCode;

    public PlayerException(String message) {
        super();
        this.message = message;
    }

    public PlayerException(String message , int errorCode){
        super();
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
