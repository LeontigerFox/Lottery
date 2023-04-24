package com.banana69.lotteryERP.interfaces.sys.service;

import com.banana69.lotteryERP.interfaces.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/19/20:34
 * @description: role service
 */
public interface IRoleService extends IService<Role> {

    void addRole(Role role);

    Role getRoleById(Integer id);

    void updateRole(Role role);

    void deleteRoleById(Integer id);
}