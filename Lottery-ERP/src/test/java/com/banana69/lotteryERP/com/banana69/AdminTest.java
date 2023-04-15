package com.banana69.lotteryERP.com.banana69;

import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/20:53
 * @description:
 */
@SpringBootTest
public class AdminTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void testMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}
