package com.banana69.lotteryERP.interfaces.sys.config.backup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/15/11:00
 * @description:
 */
//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Deprecated
public class CorsFilterConfiguration {
    @Bean
    public CorsFilter corsFilter() {

        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();

        //放行哪些原始域
        config.addAllowedOrigin("*");

        //是否发送Cookie信息
        config.setAllowCredentials(true);

        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");

        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");

        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        //config.addExposedHeader("*");

        //2.添加映射路径

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();

        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.

        return new CorsFilter(configSource);
    }


}
