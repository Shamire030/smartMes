package com.smartmes.config;

import com.smartmes.exception.BusinessException;
import com.smartmes.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 * 统一处理系统中的异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理所有未捕获的Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Void> handleException(Exception e, HttpServletRequest request) {
        // 记录异常日志
        e.printStackTrace();
        
        // 返回错误响应
        return Result.fail(500, "系统内部错误：" + e.getMessage());
    }
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

}