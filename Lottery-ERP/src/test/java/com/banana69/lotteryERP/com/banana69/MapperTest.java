package com.banana69.lotteryERP.com.banana69;

import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.mapper.MenuMapper;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserMapper;
import com.banana69.lotteryERP.interfaces.sys.service.IMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/22/19:27
 * @description:
 */
@SpringBootTest
public class MapperTest {

    @Resource
    private IMenuService menuService;

    @Resource
    private UserMapper userService;

    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuService.selectPermsByUserId(1647921497634783233L);
        System.out.println("list:"+list);
        //System.out.println(list);
    }

    @Test
    public void  testSelectOne(){
        SysUser user = userService.selectById(1647921497634783233L);
        System.out.println(user);
    }

}
