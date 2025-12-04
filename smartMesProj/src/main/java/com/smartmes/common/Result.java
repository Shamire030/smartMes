package com.smartmes.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应格式
 * @param <T>
 */
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 响应状态码
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
     * 额外信息
     */
    private Map<String, Object> extra = new HashMap<>();
    
    /**
     * 构造方法
     */
    public Result() {
    }
    
    /**
     * 构造方法
     * @param code
     * @param message
     * @param data
     */
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 获取成功响应
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }
    
    /**
     * 获取成功响应
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }
    
    /**
     * 获取失败响应
     * @param message
     * @return
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ResponseCode.ERROR.getCode(), message, null);
    }
    
    /**
     * 获取失败响应
     * @param code
     * @param message
     * @return
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 获取失败响应
     * @param responseCode
     * @return
     */
    public static <T> Result<T> error(ResponseCode responseCode) {
        return new Result<>(responseCode.getCode(), responseCode.getMessage(), null);
    }
    
    /**
     * 添加额外信息
     * @param key
     * @param value
     * @return
     */
    public Result<T> put(String key, Object value) {
        this.extra.put(key, value);
        return this;
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
    
    /**
     * 获取响应消息
     * @return
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 设置响应消息
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * 获取响应数据
     * @return
     */
    public T getData() {
        return data;
    }
    
    /**
     * 设置响应数据
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * 获取额外信息
     * @return
     */
    public Map<String, Object> getExtra() {
        return extra;
    }
    
    /**
     * 设置额外信息
     * @param extra
     */
    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
    
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", extra=" + extra +
                '}';
    }
}
