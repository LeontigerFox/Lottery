package com.banana69.lotteryERP;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/20:23
 * @description:
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/lottery_user_login?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        String moduleName = "sys";
        String mapperLocation = "/Users/null/project/Java/Lottery/Lottery-ERP/src/main/resources/mapper/" + moduleName;

        String table_prefix = "";
        String tables = "t_user,t_role_t_menu,t_user_role,t_role_menu";



        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("banana69") // 设置作者
                            .outputDir("/Users/null/project/Java/Lottery/Lottery-ERP/src/test/java/com/banana69/lotteryERP"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.banana69.lottery-erp") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("t_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
