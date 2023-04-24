package com.banana69.lotteryERP.interfaces.sys.controller;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
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
    public EasyResult<List<SysUser>> getAllUsers() {
        List<SysUser> list = userService.list();
        return  EasyResult.success(list);
    }

    /**
     * 登陆接口
     * @param sysUser
     * @return
     */
    @PostMapping("/login")
    public EasyResult<Map<String,Object>> login(@RequestBody SysUser sysUser) {
        log.warn("开始登陆");
        Map<String,Object> data = userService.login(sysUser);


        if(data != null){
            return EasyResult.success(data);
        }
        return EasyResult.fail(20002,"用户名或密码错误");
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/info")
    public EasyResult<?> getUserInfo(HttpServletRequest request){
        // 根据token获取用户信息，redis
        SysUser data = userService.getUserInfo(request);
        if(data != null){
            return EasyResult.success(data);
        }
        return EasyResult.fail(20003,"登录信息无效，请重新登录");
    }

    /**
     * 注销
     * @return
     */
    @PostMapping("/logout")
    public EasyResult<?> logout(HttpServletRequest request){
        return userService.logout(request);
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
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.checkValNotNull(username), SysUser::getUsername, username);
        wrapper.eq(StringUtils.checkValNotNull(phone), SysUser::getPhone, phone);
        wrapper.orderByAsc(SysUser::getId);

        Page<SysUser> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("total", page.getTotal());
        data.put("rows",page.getRecords());

        return EasyResult.success(data);
    }

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @PostMapping("/addUser")
    @Transactional
    @PreAuthorize("hasAnyAuthority('system:super:list')")
    public EasyResult<?> addUser(@RequestBody SysUser sysUser) {
        log.info("添加用户：" + sysUser);
       return userService.addUser(sysUser);
    }

    @PutMapping
    public  EasyResult<?> updateUer(@RequestBody SysUser sysUser){
        userService.updateUser(sysUser);
        return EasyResult.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public EasyResult<SysUser> getUserById(@PathVariable("id") Long id){
        System.out.println(id);
        SysUser sysUser = userService.getUserById(id);
        return  EasyResult.success(sysUser);
    }

    @DeleteMapping("{id}")
    public  EasyResult<SysUser> deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return EasyResult.success("删除用户成功");
    }


}
