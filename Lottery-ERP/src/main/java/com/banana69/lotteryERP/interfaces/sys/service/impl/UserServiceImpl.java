package com.banana69.lotteryERP.interfaces.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserMapper;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());

        // 根据用户名和密码进行查询
        User loginUser = this.baseMapper.selectOne(wrapper);
        if(loginUser != null){
            // 结果不为空，生成 token，并将用户信息存入redis
            // 暂时使用 uuid
            String key = "user:" + UUID.randomUUID();

            // 存入 redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,300, TimeUnit.MINUTES);


            Map<String,Object> data = new ConcurrentHashMap<>();
            data.put("token",key);
            return data;
        }

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {

        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String,Object> data = new ConcurrentHashMap<>();

            // 关联查询 查出 角色


            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());

            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);

            return data;
        }

        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
