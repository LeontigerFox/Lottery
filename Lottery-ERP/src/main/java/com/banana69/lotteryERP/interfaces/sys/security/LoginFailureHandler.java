package com.banana69.lotteryERP.interfaces.sys.security;

import com.alibaba.fastjson.JSON;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/13:11
 * @description: 登录成功处理器
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        String msg = exception.getMessage();
        if(exception instanceof BadCredentialsException){
            msg = "用户名或密码错误";
        }


        outputStream.write(JSON.toJSONString(EasyResult.fail(msg)).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
