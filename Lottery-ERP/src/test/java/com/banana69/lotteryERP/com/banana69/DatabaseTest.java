package com.banana69.lotteryERP.com.banana69;

import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.entity.UserRole;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserMapper;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/20/17:54
 * @description:
 */
@SpringBootTest
public class DatabaseTest {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserMapper userMapper;

    @Test
    public void userRole_test() {
        SysUser sysUser = userMapper.selectById(1648988175885955073L);
        System.out.println(sysUser);
        //userRoleMapper.insert(new UserRole(null, sysUser.getId(), sysUser.getRoleId() ));
    }


}
