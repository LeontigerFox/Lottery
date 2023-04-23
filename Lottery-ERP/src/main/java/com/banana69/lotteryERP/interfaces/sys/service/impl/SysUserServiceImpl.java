package com.banana69.lotteryERP.interfaces.sys.service.impl;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.common.exception.UserCountLockException;
import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.entity.UserRole;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserMapper;
import com.banana69.lotteryERP.interfaces.sys.mapper.UserRoleMapper;
import com.banana69.lotteryERP.interfaces.sys.security.impl.LoginUser;
import com.banana69.lotteryERP.interfaces.sys.service.IUserService;
import com.banana69.lotteryERP.interfaces.sys.utils.JwtUtil;
import com.banana69.lotteryERP.interfaces.sys.utils.RedisUtils;
import com.banana69.lotteryERP.interfaces.sys.utils.SHACoder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements IUserService {

    @Autowired
    private RedisUtils redisUtils;


    @Resource
    private  UserRoleMapper userRoleMapper;


    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, Object> login(SysUser sysUser) {
        log.warn("登录用户：" +sysUser.toString());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            // 未通过，给出提示
            throw new UsernameNotFoundException("用户名或密码错误");
        }else{
            // 认证通过 生成 jwt
            String token = JwtUtil.createToken(sysUser);
            Map<String,Object> data = new ConcurrentHashMap<>();
            data.put("authorization",token);

            LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

            // 存入 redis
            sysUser.setPassword(null);
            redisUtils.setCacheObject(token, loginUser,20,TimeUnit.MINUTES);

            return data;

        }

    }

    @Override
    public SysUser getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null){
            try {
                SysUser loginSysUser = JwtUtil.parseToken(token, SysUser.class);
                loginSysUser = this.getByUsername(loginSysUser.getUsername());
                return loginSysUser;

            } catch (Exception e) {
                throw  new RuntimeException("Token 不合法");
            }
        }

        //if(loginSysUser != null){
        //    Map<String,Object> data = new ConcurrentHashMap<>();
        //    data.put("name", loginSysUser.getUsername());
        //    data.put("avatar", StringUtils.isEmpty(loginSysUser.getAvatar()) ? "" : loginSysUser.getAvatar());
        //
        //    // 角色
        //    List<String> roleList = this.baseMapper.getRoleNameByUserId(loginSysUser.getId());
        //    data.put("roles",roleList);
        //
        //    // 权限
        //    List<Menu> menuList = menuService.getMenuListByUserId(loginSysUser.getId());
        //    data.put("menuList",menuList);
        //
        //
        //    return data;
        //}

        return null;
    }

    @Override
    public EasyResult<?> logout(HttpServletRequest request) {
        // 获取SecurityContextHolder中的用户
        String token = request.getHeader("Authorization");
        System.out.println("注销用户： " + token);
        UsernamePasswordAuthenticationToken authUser =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // 删除redis中的值
        redisUtils.deleteObject(token);
        return EasyResult.success("注销成功");
    }

    @Override
    public EasyResult<?> addUser(SysUser sysUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            SysUser addSysUser = query().eq("uid", SHACoder.encodeSHA256Hex(sysUser.getUsername())).one();
            if(addSysUser != null){
                return EasyResult.fail("添加失败，该用户已存在");
            }else {
                sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
                sysUser.setUid(SHACoder.encodeSHA256Hex(sysUser.getUsername()));
                this.save(sysUser);

                return EasyResult.success(sysUser);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return EasyResult.fail("添加用户异常");
    }

    @Override
    public SysUser getUserById(Long id) {
        SysUser sysUser = this.baseMapper.selectById(id);
        System.out.println(sysUser);
        SysUser frontSysUser = new SysUser();

        BeanUtils.copyProperties(sysUser, frontSysUser, "password");
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id );
        List<UserRole> userRoles = userRoleMapper.selectList(wrapper);
        List<Integer> roleList = userRoles.stream().map(userRole -> {
            return userRole.getRoleId();
        }).collect(Collectors.toList());
        //frontSysUser.setRoleIdList(roleList);
        return frontSysUser;
    }

    @Override
    public void updateUser(SysUser sysUser) {
        this.baseMapper.updateById(sysUser);
        //// 清除原有角色
        //LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        //wrapper.eq(UserRole::getUserId, sysUser.getId());
        //userRoleMapper.delete(wrapper);
        //
        //// 设置新的角色
        //List<Integer> roleIdList = sysUser.getRoleIdList();
        //if(roleIdList != null){
        //    for(Integer roleId: roleIdList){
        //        userRoleMapper.insert(new UserRole(null, sysUser.getId(), roleId));
        //    }
        //}
    }

    @Override
    public void deleteUserById(Long id) {
        this.baseMapper.deleteById(id);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id );
        userRoleMapper.delete(wrapper);
    }

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser user = this.baseMapper.selectOne(wrapper);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }else if(user.getStatus() == 0){
            throw new UserCountLockException("该用户已禁用");
        }

        // TODO 查询权限信息
        return user;
    }
}
