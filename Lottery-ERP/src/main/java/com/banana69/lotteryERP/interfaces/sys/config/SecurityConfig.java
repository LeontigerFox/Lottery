package com.banana69.lotteryERP.interfaces.sys.config;

import com.banana69.lotteryERP.interfaces.sys.interceptor.JwtAuthenticationTokenFilter;
import com.banana69.lotteryERP.interfaces.sys.security.LoginFailureHandler;
import com.banana69.lotteryERP.interfaces.sys.security.LoginSuccessHandler;
import com.banana69.lotteryERP.interfaces.sys.security.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/11:58
 * @description: spring security 配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private static final String[] URL_WHITELIST = {
        "/user/login",
        "/login",
        "/user/**",
    };



    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
           .csrf().disable()
           // 不通过 session 获取 SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           .and()
               .authorizeRequests()
               // 对于登录接口允许匿名访问
               .antMatchers(URL_WHITELIST).permitAll()
               .antMatchers("/user/login","/login").anonymous()
               // 其他请求需要鉴权
               .anyRequest().authenticated();

       // 添加过滤器
       http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置异常处理器
        http.exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler);
        // 允许跨域
        http.cors();

    }



}
