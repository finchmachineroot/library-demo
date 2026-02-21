package com.example.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 核心注解：像雷达一样监听整个项目的所有接口
@Slf4j // 用于记录日志，方便以后排查线上问题
public class GlobalExceptionHandler {

    /**
     * 捕获所有未知的系统异常 (比如 Redis 突然断开了)
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        log.error("系统运行出错: ", e); // 在控制台打印详细错误，方便你调试
        return Result.error("服务器正在维护，请稍后再试"); // 给前端一个体面的交代
    }

    /**
     * 专门捕获参数校验异常 (比如 @NotBlank 触发了)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleValidationException(MethodArgumentNotValidException e) {
        // 抓取你在实体类 @NotBlank(message = "...") 里写的那个提示语
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(message);
    }
}
