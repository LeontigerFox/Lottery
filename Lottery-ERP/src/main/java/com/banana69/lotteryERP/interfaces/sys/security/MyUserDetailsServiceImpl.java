package com.banana69.lotteryERP.interfaces.sys.security;

import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.mapper.MenuMapper;
import com.banana69.lotteryERP.interfaces.sys.security.impl.LoginUser;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/21/19:14
 * @description:
 */
@Service
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IUserService userService;

    @Resource
    private MenuMapper menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getByUsername(username);



        // 查询对应的权限信息
        List<String> list = menuService.selectPermsByUserId(sysUser.getId());
        log.warn("权限信息 ：" + list);



        return new LoginUser(sysUser,list);
    }

    private List< GrantedAuthority> getUserAuthority() {
        return new ArrayList<GrantedAuthority>();
    }
}
