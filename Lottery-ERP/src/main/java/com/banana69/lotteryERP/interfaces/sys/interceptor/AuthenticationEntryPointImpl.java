package com.banana69.lotteryERP.interfaces.sys.interceptor;

import com.alibaba.fastjson.JSON;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/22/21:54
 * @description: 调用失败过滤器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        EasyResult<?> result = EasyResult.fail(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重新登录");
        String jsonString = JSON.toJSONString(result);
        // 处理异常
        WebUtils.renderString(response, jsonString);
    }
}
