package com.banana69.lotteryERP.interfaces.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserMapper;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import com.banana69.lotteryERP.utils.JwtUtil;
import com.banana69.lotteryERP.utils.SHACoder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(User user) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        // 根据用户名进行查询
        User loginUser = this.baseMapper.selectOne(wrapper);

        if(loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){
            // 结果不为空，生成 token，并将用户信息存入redis
            //// 暂时使用 uuid
            //String key = "user:" + UUID.randomUUID();

            String token = jwtUtil.createToken(loginUser);


            // 存入 redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(token,loginUser,300, TimeUnit.MINUTES);


            Map<String,Object> data = new ConcurrentHashMap<>();
            data.put("token",token);
            return data;
        }

        //// 根据用户名和密码进行查询
        //User loginUser = this.baseMapper.selectOne(wrapper);
        //if(loginUser != null){
        //    // 结果不为空，生成 token，并将用户信息存入redis
        //    // 暂时使用 uuid
        //    String key = "user:" + UUID.randomUUID();
        //
        //    // 存入 redis
        //    loginUser.setPassword(null);
        //    redisTemplate.opsForValue().set(key,loginUser,300, TimeUnit.MINUTES);
        //
        //
        //    Map<String,Object> data = new ConcurrentHashMap<>();
        //    data.put("token",key);
        //    return data;
        //}

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {

        Object obj = redisTemplate.opsForValue().get(token);
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loginUser != null){
            Map<String,Object> data = new ConcurrentHashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar", StringUtils.isEmpty(loginUser.getAvatar()) ? null : loginUser.getAvatar());


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

    @Override
    public EasyResult<?> addUser(User user) {
        try {
            User addUser = query().eq("uid", SHACoder.encodeSHA256Hex(user.getUsername())).one();
            if(addUser != null){
                return EasyResult.fail("添加失败，该用户已存在");
            }else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUid(SHACoder.encodeSHA256Hex(user.getUsername()));
                this.save(user);
                return EasyResult.success(user);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return EasyResult.fail("添加用户异常");
    }
}
