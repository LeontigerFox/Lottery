package com.banana69.lotteryERP.com.banana69;

import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/17/21:11
 * @description:
 */
@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        SysUser sysUser = new SysUser();
        sysUser.setPassword("admin");
        sysUser.setPassword("admin");

        String token = jwtUtil.createToken(sysUser);

        System.out.println(token);
    }

    @Test
    public  void testParseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZTVlYmVhNy02ZjQyLTQ4NGYtOGRiMy03JjhiYmE5NDE1ZDkiLCJzdWIiOiJ7XCJwYXNzd29yZFwiOlwiYWRtaW5cIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2ODE3Mzc4NDQsImV4cCI6MTY4MTczOTY0NH0.Sw6Z4B1a6i2vYNuhlSdQbAS9mCQiCG4-oc0n5s8Ryks";
        Claims claims = jwtUtil.parseToken(token);
        SysUser sysUser = jwtUtil.parseToken(token, SysUser.class);
        System.out.println(claims);
        System.out.println(sysUser);
    }


}
