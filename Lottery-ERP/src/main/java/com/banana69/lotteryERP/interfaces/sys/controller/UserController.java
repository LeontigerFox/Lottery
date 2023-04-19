package com.banana69.lotteryERP.interfaces.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/17/14:16
 * @description:  user controller
 */
@RestController
@RequestMapping("/user")
//@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/all")
    public EasyResult<List<User>> getAllUsers() {
        List<User> list = userService.list();
        return  EasyResult.success(list);
    }

    /**
     * 登陆接口
     * @param user
     * @return
     */
    @PostMapping("/login")
    public EasyResult<Map<String,Object>> login(@RequestBody User user) {
        Map<String,Object> data = userService.login(user);

        if(data != null){
            return EasyResult.success(data);
        }
        return EasyResult.fail(20002,"用户名或密码错误");
    }

    /**
     * 查询用户信息
     * @param token
     * @return
     */
    @GetMapping("/info")
    public EasyResult<Map<String,Object>> getUserInfo(@RequestParam(value = "token", required = false) String token){
        // 根据token获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return EasyResult.success(data);
        }
        return EasyResult.fail(20003,"登录信息无效，请重新登录");
    }

    /**
     * 注销
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public EasyResult<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return EasyResult.success();
    }

    /**
     * 分页查询
     * @param username
     * @param phone
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public EasyResult<Map<String, Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                   @RequestParam(value = "phone",required = false) String phone,
                                                   @RequestParam(value = "pageNo",required = false) Long pageNo,
                                                   @RequestParam(value = "pageSize",required = false) Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.checkValNotNull(username),User::getUsername, username);
        wrapper.eq(StringUtils.checkValNotNull(phone),User::getPhone, phone);
        wrapper.orderByAsc(User::getId);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("total", page.getTotal());
        data.put("rows",page.getRecords());

        return EasyResult.success(data);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public EasyResult<?> addUser(@RequestBody User user) {
       return userService.addUser(user);
    }

    @PutMapping
    public  EasyResult<?> updateUer(@RequestBody User user){
        userService.updateById(user);
        return EasyResult.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public EasyResult<User> getUserById(@PathVariable("id") Long id){
        System.out.println(id);
        User user = userService.getById(id);

        User frontUser = new User();
        BeanUtil.copyProperties(user, frontUser, "password");

        return  EasyResult.success(frontUser);
    }

    @DeleteMapping("{id}")
    public  EasyResult<User> deleteUser(@PathVariable("id") Long id){
        userService.removeById(id);
        return EasyResult.success("删除用户成功");
    }


}
