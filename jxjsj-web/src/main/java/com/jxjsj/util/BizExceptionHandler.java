package com.jxjsj.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by niyang on 2017/8/11.
 */
@ControllerAdvice
@ResponseBody
public class BizExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public Object BizExceptionHandler(HttpServletRequest request, Exception e) {
        RestResult restResult = new RestResult<>();
        if (e instanceof BizException) {
            e.printStackTrace();
            BizException bizException = (BizException) e;
            restResult.setResultCode(bizException.getCode());
            restResult.setResultMsg(bizException.getMsg());
        }else if (e instanceof SQLException) {
            e.printStackTrace();
            restResult.setResultCode(600);
            restResult.setResultMsg(e.getMessage());
        }else {
            e.printStackTrace();
            restResult.setResultCode(500);
            restResult.setResultMsg(e.getMessage());
        }
        return restResult;
    }
}
