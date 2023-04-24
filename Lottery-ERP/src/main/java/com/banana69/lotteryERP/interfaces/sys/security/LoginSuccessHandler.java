package com.banana69.lotteryERP.interfaces.sys.security;

import com.alibaba.fastjson.JSON;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        System.out.println("request" + request);
        SysUser sysUser = new SysUser();
        sysUser.setUsername("admin");
        String token = JwtUtil.createToken(sysUser);


        outputStream.write(JSON.toJSONString(EasyResult.success("Authorization",token)).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
