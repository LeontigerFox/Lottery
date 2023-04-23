package com.banana69.lotteryERP.interfaces.sys.common.exception;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/19:30
 * @description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public EasyResult<?> handler(RuntimeException e){
        log.error("运行时异常： ------ {}"+e.getMessage());
        return EasyResult.fail(e.getMessage());
    }
}
