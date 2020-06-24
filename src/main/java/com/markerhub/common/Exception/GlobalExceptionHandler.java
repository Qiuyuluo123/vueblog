package com.markerhub.common.Exception;

import com.markerhub.common.Lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)//判断状态是否正常
    @ExceptionHandler(value = ShiroException.class)//捕获shiro异常
    public Result handler(ShiroException e){
        log.error("运行时异常：----------------{}",e);
        return Result.fail(401,e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)//判断状态是否正常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)//捕获运行时异常
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体校验异常：----------------{}",e);
        BindingResult bindingResult=e.getBindingResult();
        ObjectError objectError=bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)//判断状态是否正常
    @ExceptionHandler(value = RuntimeException.class)//捕获运行时异常
    public Result handler(RuntimeException e){
        log.error("运行时异常：----------------{}",e);
        return Result.fail(e.getMessage());
    }

}
