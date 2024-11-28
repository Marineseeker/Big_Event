package com.example.demo.exception;

import com.example.demo.annotation.log;
import com.example.demo.pojo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获参数验证失败的异常
     */
    @log
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> Result<T> handleValidationException(MethodArgumentNotValidException e) {
        // 获取所有字段错误信息
        List<String> errorMessages = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        // 将错误信息拼接成一个字符串
        String errorMessage = String.join("; ", errorMessages);

        // 返回拼接后的错误信息
        return Result.error(errorMessage);
    }

    // 捕获 SQL 约束异常，比如重复键、非空约束等
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public <T> Result<T> handleSQLIntegrityException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        if (message.contains("cannot be null")) {
            message = "必填字段不能为空";
        } else if (message.contains("Duplicate entry")) {
            message = "数据重复，操作失败";
        }
        return Result.error(message);
    }

    // 捕获所有其他异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> Result<T> handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "服务器异常");
    }
}
