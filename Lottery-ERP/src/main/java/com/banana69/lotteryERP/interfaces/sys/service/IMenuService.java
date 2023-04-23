package com.banana69.lotteryERP.interfaces.sys.service;

import com.banana69.lotteryERP.interfaces.sys.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/19/20:35
 * @description:
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    List<Menu> getMenuListByUserId(Long userId);

    List<String> selectPermsByUserId(Long userId);
}
