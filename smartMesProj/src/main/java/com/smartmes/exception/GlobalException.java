package com.smartmes.exception;

import com.smartmes.common.ResponseCode;

/**
 * 全局自定义异常
 */
public class GlobalException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 响应状态码
     */
    private int code;
    
    /**
     * 构造方法
     */
    public GlobalException() {
        super();
    }
    
    /**
     * 构造方法
     * @param message
     */
    public GlobalException(String message) {
        super(message);
        this.code = ResponseCode.ERROR.getCode();
    }
    
    /**
     * 构造方法
     * @param code
     * @param message
     */
    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造方法
     * @param responseCode
     */
    public GlobalException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }
    
    /**
     * 构造方法
     * @param message
     * @param cause
     */
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResponseCode.ERROR.getCode();
    }
    
    /**
     * 构造方法
     * @param code
     * @param message
     * @param cause
     */
    public GlobalException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    /**
     * 构造方法
     * @param responseCode
     * @param cause
     */
    public GlobalException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
    }
    
    /**
     * 构造方法
     * @param cause
     */
    public GlobalException(Throwable cause) {
        super(cause);
        this.code = ResponseCode.ERROR.getCode();
    }
    
    /**
     * 获取响应状态码
     * @return
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 设置响应状态码
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }
}
