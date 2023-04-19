package com.banana69.lotteryERP.interfaces.sys.service;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    EasyResult<?>  addUser(User user);
}
