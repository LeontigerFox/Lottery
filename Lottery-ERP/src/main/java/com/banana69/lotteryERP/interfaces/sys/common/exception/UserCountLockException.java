package com.banana69.lotteryERP.interfaces.sys.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/19:23
 * @description:
 */
public class UserCountLockException extends AuthenticationException {
    public UserCountLockException(String explanation,Throwable cause) {
        super(explanation,cause);
    }

    public UserCountLockException(String msg) {
        super(msg);
    }
}
