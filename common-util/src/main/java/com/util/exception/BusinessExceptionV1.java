package com.util.exception;

/**
 * 业务异常定义1
 */
public class BusinessExceptionV1 extends IllegalStateException {

    private String reason;

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}
