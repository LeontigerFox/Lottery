package com.banana69.lotteryERP.interfaces.sys.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/21:15
 * @description: 将字符串渲染到客户端
 */
public class WebUtils {
    public static String renderString(HttpServletResponse response, String string){
        try{
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
