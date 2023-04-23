package com.banana69.lotteryERP.interfaces.sys.controller;

import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.sys.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/20/15:04
 * @description:
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    //@Autowired
    //private IMenuService menuService;
    //
    //@GetMapping
    //public EasyResult<List<Menu>> getAllMenu() {
    //    List<Menu> allMenu = menuService.getAllMenu();
    //    return EasyResult.success(allMenu);
    //}
}
