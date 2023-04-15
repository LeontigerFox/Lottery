package com.banana69.lotteryERP;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/14/16:30
 * @description:  抽奖系统 ERP Application
 */
@MapperScan(value = "com.banana69.lotteryERP.interfaces.sys.mapper")
@SpringBootApplication()
public class LotteryErpApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LotteryErpApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LotteryErpApplication.class);
    }


}
