package com.banana69.lotteryERP.interfaces.sys.interceptor;

import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.security.impl.LoginUser;
import com.banana69.lotteryERP.interfaces.sys.utils.JwtUtil;
import com.banana69.lotteryERP.interfaces.sys.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/22:05
 * @description: JWT 过滤器
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }
        // 解析 token
        try {
            SysUser sysUser = JwtUtil.parseToken(token,SysUser.class);
            if(Objects.isNull(sysUser)){
                throw new RuntimeException("用户未登录");
            }


            // TODO 获取权限信息封装到 authToken

            LoginUser loginUser = redisUtils.getCacheObject(token) ;
            System.out.println("******************"+loginUser);
            // 存入 SecurityContextHolder
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(sysUser, null,loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            //throw new RuntimeException("非法 token");
            e.printStackTrace();
        }






    }
}
