package com.banana69.lotteryERP.interfaces.sys.service;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
public interface IUserService extends IService<SysUser> {

    Map<String, Object> login(SysUser sysUser);

    SysUser getUserInfo(HttpServletRequest request);

    EasyResult<?> logout(HttpServletRequest request);

    EasyResult<?>  addUser(SysUser sysUser);

    SysUser getUserById(Long id);

    void updateUser(SysUser sysUser);

    void deleteUserById(Long id);


    SysUser getByUsername(String username);
}
