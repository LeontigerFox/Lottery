package com.banana69.lotteryERP.interfaces.sys.service.impl;

import com.banana69.lotteryERP.interfaces.sys.entity.Role;
import com.banana69.lotteryERP.interfaces.sys.entity.RoleMenu;
import com.banana69.lotteryERP.interfaces.sys.mapper.RoleMapper;
import com.banana69.lotteryERP.interfaces.sys.mapper.RoleMenuMapper;
import com.banana69.lotteryERP.interfaces.sys.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/19/20:36
 * @description:
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public void addRole(Role role) {
        // 写入角色表
        this.baseMapper.insert(role);
        //System.out.println(role.getMenuIdList());
        //// 写入角色菜单关系表
        //if( null != role.getMenuIdList()){
        //    System.out.println("开始插入，********");
        //    for (Integer menuId : role.getMenuIdList()) {
        //        roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
        //    }
        //}
    }

    @Override
    public Role getRoleById(Integer id) {
        Role role = this.baseMapper.selectById(id);
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        //role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        // 修改角色表
        this.baseMapper.updateById(role);
        // 删除原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getId,role.getId());
        roleMenuMapper.delete(wrapper);
        // 新增权限
        //if( null != role.getMenuIdList()){
        //    for (Integer menuId : role.getMenuIdList()) {
        //        roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
        //    }
        //}
    }

    @Override
    @Transactional
    public void deleteRoleById(Integer id) {
        this.baseMapper.deleteById(id);
        // 删除权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(wrapper);
    }

}