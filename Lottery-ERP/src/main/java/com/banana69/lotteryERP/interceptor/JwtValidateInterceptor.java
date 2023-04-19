package com.banana69.lotteryERP.interceptor;

import com.alibaba.fastjson.JSON;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.banana69.lotteryERP.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/17/21:50
 * @description: jwt 拦截器
 */
@Slf4j
@Component
public class JwtValidateInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        log.info(request.getRequestURI() + "需要验证： "+ token);
        if(token != null){
            try {
                jwtUtil.parseToken(token);
                log.info(request.getRequestURI() + "验证通过");
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.info("验证失败，禁止访问");
        response.setContentType("application/json;charset=UTF-8");
        EasyResult<Object> fail = EasyResult.fail("jwt无效，请重新登录");
        response.getWriter().write(JSON.toJSONString(fail));
        return false;
    }
}
