package com.banana69.lotteryERP.interfaces.sys.mapper;

import com.banana69.lotteryERP.interfaces.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
public interface UserMapper extends BaseMapper<SysUser> {

     List<String> getRoleNameByUserId(Long userId);
}
