package com.qf.handler;

import com.qf.entity.BusinessException;
import com.qf.entity.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ShopExceptionHandler {

    // 业务相关的异常
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultEntity businessException(BusinessException e){
        System.out.println("RBACExceptionHandler.businessException");
        // 记录异常到日志
        log.error(e.getMsg(),e);

        // 响应给用户
        return ResultEntity.error(e.getMsg());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultEntity systemException(Exception e){

        // 记录异常到日志
        log.error("系统异常",e);

        // 响应给用户
        return ResultEntity.error("系统正在维护。。。。。");
    }
}
