//package com.banana69.lotteryERP.config.backup;
//
//import com.banana69.lotteryERP.interceptor.JwtValidateInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @author: banana69
// * @date: 2023/04/17/22:01
// * @description: 拦截器
// */
//public class MyInterceptorConfig  implements WebMvcConfigurer {
//
//    @Autowired
//    private JwtValidateInterceptor jwtValidateInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
//        registration.addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/user/login",
//                        "/user/info",
//                        "/user/logout",
//                        "/error",
//                        "/swagger-ui/**",
//                        "/swagger-resources/**",
//                        "/v3/**");
//    }
//}
