package com.smartmes.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应结果类
 * 用于API接口的标准化响应格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 响应码：0表示成功，非0表示失败
     */
    private int code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data);
    }
    
    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(0, "success", null);
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 默认失败响应
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null);
    }
    
    /**
     * 错误响应（与fail方法功能相同，保持兼容性）
     */
    public static <T> Result<T> error(String message) {
        return fail(message);
    }
    
    /**
     * 错误响应（与fail方法功能相同，保持兼容性）
     */
    public static <T> Result<T> error(int code, String message) {
        return fail(code, message);
    }

}