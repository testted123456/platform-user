package com.nonobank.platformuser.component;

import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tangrubei on 2018/3/1.
 */
@ControllerAdvice
public class RepExceptionHandler {
    public static Logger logger = LoggerFactory.getLogger(RepExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity handle(Exception e){
        if( e instanceof MyUserException){
            MyUserException userException = (MyUserException)e;
            return ResponseUtil.error(userException.getErrorCode(), userException.getErrorMessage());
        }else{
            logger.error("发生未知异常");
            e.printStackTrace();
            return ResponseUtil.error(ResponseCode.UNKOWN_ERROR.getCode(), e.getClass().getName());
        }
    }
}
