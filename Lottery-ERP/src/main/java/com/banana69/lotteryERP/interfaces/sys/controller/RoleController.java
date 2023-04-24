package com.banana69.lotteryERP.interfaces.sys.controller;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.Role;
import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.banana69.lotteryERP.interfaces.sys.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/17/14:16
 * @description:  role controller
 */
@RestController
@RequestMapping("/role")
//@CrossOrigin
@Slf4j
public class RoleController {

    @Autowired
    private IRoleService roleService;



    /**
     * 分页查询
     * @param roleName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public EasyResult<Map<String, Object>> getUserList(@RequestParam(value = "name",required = false) String name,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.checkValNotNull(name),Role::getName, name);
        wrapper.orderByAsc(Role::getId);

        Page<Role> page = new Page<>(pageNo,pageSize);
        System.out.println(page);
        roleService.page(page,wrapper);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("total", page.getTotal());
        data.put("rows",page.getRecords());

        return EasyResult.success(data);
    }

    /**
     * 新增
     * @param role
     * @return
     */
    @PostMapping("/addRole")
    public EasyResult<?> addRole(@RequestBody Role role) {
        System.out.println(role);
        roleService.addRole(role);
        return EasyResult.success("新增角色成功");
    }

    @PutMapping
    public EasyResult<?> updateRole(@RequestBody Role role) {
        System.out.println(role);
        roleService.updateRole(role);
        return EasyResult.success("修改角色成功");
    }

    @GetMapping("/{id}")
    public EasyResult<Role> getRoleById(@PathVariable("id") Integer id){
        Role role = roleService.getRoleById(id);
        return  EasyResult.success(role);
    }

    @DeleteMapping("{id}")
    public  EasyResult<SysUser> deleteRole(@PathVariable("id") Integer id){
        roleService.deleteRoleById(id);
        return EasyResult.success("删除用户成功");
    }

    @GetMapping("/all")
    public EasyResult<List<Role>> getAllRole(){
        List<Role> roleList = roleService.list();
        return EasyResult.success(roleList);
    }


}
