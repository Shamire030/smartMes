package com.smartmes.common;

/**
 * 响应状态码枚举
 */
public enum ResponseCode {
    
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    
    /**
     * 失败
     */
    ERROR(500, "操作失败"),
    
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "未授权"),
    
    /**
     * 未登录
     */
    NO_LOGIN(402, "未登录"),
    
    /**
     * 无权限
     */
    FORBIDDEN(403, "无权限"),
    
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    
    /**
     * 请求参数错误
     */
    PARAM_ERROR(400, "请求参数错误"),
    
    /**
     * 数据已存在
     */
    DATA_EXIST(409, "数据已存在"),
    
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(410, "数据不存在"),
    
    /**
     * 业务错误
     */
    BUSINESS_ERROR(422, "业务错误"),
    
    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(503, "系统繁忙"),
    
    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),
    
    /**
     * 数据库操作失败
     */
    DATABASE_ERROR(501, "数据库操作失败"),
    
    /**
     * 网络错误
     */
    NETWORK_ERROR(502, "网络错误"),
    
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(601, "文件上传失败"),
    
    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_ERROR(602, "文件下载失败"),
    
    /**
     * 文件不存在
     */
    FILE_NOT_EXIST(603, "文件不存在"),
    
    /**
     * 文件格式错误
     */
    FILE_FORMAT_ERROR(604, "文件格式错误"),
    
    /**
     * 文件大小错误
     */
    FILE_SIZE_ERROR(605, "文件大小错误"),
    
    /**
     * 导入失败
     */
    IMPORT_ERROR(701, "导入失败"),
    
    /**
     * 导出失败
     */
    EXPORT_ERROR(702, "导出失败"),
    
    /**
     * 计算失败
     */
    CALCULATE_ERROR(801, "计算失败"),
    
    /**
     * 验证失败
     */
    VALIDATE_ERROR(802, "验证失败"),
    
    /**
     * 加密失败
     */
    ENCRYPT_ERROR(803, "加密失败"),
    
    /**
     * 解密失败
     */
    DECRYPT_ERROR(804, "解密失败"),
    
    /**
     * 超时
     */
    TIMEOUT(901, "超时"),
    
    /**
     * 并发错误
     */
    CONCURRENCY_ERROR(902, "并发错误"),
    
    /**
     * 限流
     */
    RATE_LIMIT(903, "限流"),
    
    /**
     * 熔断
     */
    CIRCUIT_BREAKER(904, "熔断"),
    
    /**
     * 降级
     */
    DEGRADE(905, "降级");
    
    /**
     * 状态码
     */
    private final int code;
    
    /**
     * 状态消息
     */
    private final String message;
    
    /**
     * 构造方法
     * @param code
     * @param message
     */
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * 获取状态码
     * @return
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 获取状态消息
     * @return
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 根据状态码获取枚举
     * @param code
     * @return
     */
    public static ResponseCode getByCode(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.getCode() == code) {
                return responseCode;
            }
        }
        return null;
    }
}
