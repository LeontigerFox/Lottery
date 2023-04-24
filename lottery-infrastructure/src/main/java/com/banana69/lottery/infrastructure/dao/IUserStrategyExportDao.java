package com.banana69.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.banana69.lottery.infrastructure.po.UserStrategyExport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/11:29
 * @description: 用户策略计算结果表DAO
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserStrategyExportDao extends BaseMapper<UserStrategyExport> {

    /**
     * 新增数据
     * @param userStrategyExport 用户策略
     */
    @DBRouter(key = "uId")
    void insertUserStrategy(UserStrategyExport userStrategyExport);

    /**
     * 查询数据
     * @param uId 用户ID
     * @return 用户策略
     */
    @DBRouter
    UserStrategyExport queryUserStrategyExportByUId(String uId);

    /**
     * 更新发送MQ状态
     * @param userStrategyExport
     */
    @DBRouter
    void updateUserAwardState(UserStrategyExport userStrategyExport);

    @DBRouter
    void updateInvoiceMqState(UserStrategyExport userStrategyExport);
}
