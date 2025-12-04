package com.smartmes.exception;

import lombok.Getter;

/**
 * 业务异常类
 * 用于表示业务逻辑中的异常
 */
@Getter
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final int code;
    
    /**
     * 构造方法
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造方法（使用默认错误码）
     * @param message 错误消息
     */
    public BusinessException(String message) {
        this(500, message);
    }
    
    /**
     * 构造方法（包含原异常）
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原异常
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}