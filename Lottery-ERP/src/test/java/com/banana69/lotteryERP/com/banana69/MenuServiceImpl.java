//package com.banana69.lotteryERP.com.banana69;
//
//import com.banana69.lotteryERP.interfaces.sys.entity.Menu;
//import com.banana69.lotteryERP.interfaces.sys.service.IMenuService;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @author: banana69
// * @date: 2023/04/19/20:35
// * @description:
// */
//@Service
//public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
//
//
//    @Override
//    public List<Menu> getAllMenu() {
//        // 一级菜单
//        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Menu::getParentId,0);
//        List<Menu> menuList = this.list(wrapper);
//        // 填充子菜单
//        setMenuChildren(menuList);
//        return menuList;
//    }
//
//    private void setMenuChildren( List<Menu> menuList) {
//        if(menuList != null){
//            for(Menu menu: menuList){
//                LambdaQueryWrapper<Menu> subWrapper = new LambdaQueryWrapper<>();
//                subWrapper.eq(Menu::getParentId,menu.getMenuId());
//                List<Menu> subMenuList = this.list(subWrapper);
//                menu.setChildren(subMenuList);
//                // 递归
//                setMenuChildren(subMenuList);
//            }
//        }
//    }
//
//    @Override
//    public List<Menu> getMenuListByUserId(Long userId) {
//        // 一级菜单
//        List<Menu> menuList = this.baseMapper.getMunuListByUserId(userId, 0);
//        setMenuChildrenByUserId(userId, menuList);
//        // 子菜单
//        return menuList;
//    }
//
//    private void setMenuChildrenByUserId(Long userId, List<Menu> menuList) {
//        if(menuList != null){
//            for(Menu menu: menuList){
//                List<Menu> subMenuList = this.baseMapper.getMunuListByUserId(userId, menu.getMenuId());
//                menu.setChildren(subMenuList);
//                setMenuChildrenByUserId(userId, subMenuList);
//            }
//        }
//    }
//}