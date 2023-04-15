package com.banana69.lotteryERP.interfaces.sys.controller;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
@RestController
@RequestMapping("/user")
//@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public EasyResult<List<User>> getAllUsers() {
        List<User> list = userService.list();
        return  EasyResult.success(list);
    }

    @PostMapping("/login")
    public EasyResult<Map<String,Object>> login(@RequestBody User user) {
        Map<String,Object> data = userService.login(user);

        if(data != null){
            return EasyResult.success(data);
        }
        return EasyResult.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public EasyResult<Map<String,Object>> getUserInfo(@RequestParam(value = "token", required = false) String token){
        // 根据token获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return EasyResult.success(data);
        }
        return EasyResult.fail(20003,"登录信息无效，请重新登录");
    }

    @PostMapping("/logout")
    public EasyResult<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return EasyResult.success();
    }


}
