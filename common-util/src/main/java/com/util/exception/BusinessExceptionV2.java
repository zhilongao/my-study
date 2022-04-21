package com.util.exception;

/**
 * 业务异常体系的优化。。。。。。。。。
 * 业务异常定义2 重写fillInStackTrace,提高异常处理效率
 */
public class BusinessExceptionV2 extends IllegalStateException {

    private String reason;


    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
