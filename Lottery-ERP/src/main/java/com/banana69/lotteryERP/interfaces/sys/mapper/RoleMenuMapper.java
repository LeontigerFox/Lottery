package com.banana69.lotteryERP.interfaces.sys.mapper;

import com.banana69.lotteryERP.interfaces.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Integer> getMenuIdListByRoleId(Integer roleId);

}
