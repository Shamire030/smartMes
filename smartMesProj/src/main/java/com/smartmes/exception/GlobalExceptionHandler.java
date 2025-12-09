package com.smartmes.exception;

import com.smartmes.common.Result;
import com.smartmes.common.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理自定义异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public Result<?> handleGlobalException(HttpServletRequest request, GlobalException e) {
        logger.error("GlobalException: {}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理方法参数校验异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException: {}", e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return Result.error(ResponseCode.VALIDATE_ERROR.getCode(), "参数校验失败").put("errors", errors);
    }
    
    /**
     * 处理数据库异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public Result<?> handleSQLException(HttpServletRequest request, SQLException e) {
        logger.error("SQLException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.DATABASE_ERROR.getCode(), "数据库操作失败");
    }
    
    /**
     * 处理资源不存在异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result<?> handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
        logger.error("NoHandlerFoundException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.NOT_FOUND.getCode(), "请求的资源不存在");
    }
    
    /**
     * 处理空指针异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result<?> handleNullPointerException(HttpServletRequest request, NullPointerException e) {
        logger.error("NullPointerException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), "系统异常：空指针");
    }
    
    /**
     * 处理数组越界异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public Result<?> handleArrayIndexOutOfBoundsException(HttpServletRequest request, ArrayIndexOutOfBoundsException e) {
        logger.error("ArrayIndexOutOfBoundsException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), "系统异常：数组越界");
    }
    
    /**
     * 处理类型转换异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public Result<?> handleClassCastException(HttpServletRequest request, ClassCastException e) {
        logger.error("ClassCastException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), "系统异常：类型转换");
    }
    
    /**
     * 处理算术异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<?> handleArithmeticException(HttpServletRequest request, ArithmeticException e) {
        logger.error("ArithmeticException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), "系统异常：算术错误");
    }
    
    /**
     * 处理字符串转换异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public Result<?> handleNumberFormatException(HttpServletRequest request, NumberFormatException e) {
        logger.error("NumberFormatException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), "系统异常：数字格式错误");
    }
    
    /**
     * 处理运行时异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result<?> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        logger.error("RuntimeException: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), e.getMessage());
    }
    
    /**
     * 处理所有异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handleException(HttpServletRequest request, Exception e) {
        logger.error("Exception: {}", e.getMessage(), e);
        return Result.error(ResponseCode.ERROR.getCode(), "系统异常");
    }
    
    /**
     * 获取请求IP
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}


