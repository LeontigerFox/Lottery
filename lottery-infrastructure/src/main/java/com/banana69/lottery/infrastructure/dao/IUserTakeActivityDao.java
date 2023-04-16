package com.banana69.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.banana69.lottery.infrastructure.po.UserStrategyExport;
import com.banana69.lottery.infrastructure.po.UserTakeActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/11:31
 * @description:
 */
@Mapper
public interface IUserTakeActivityDao extends BaseMapper<UserTakeActivity> {

    /**
     * 插入用户领取活动信息
     *
     * @param userTakeActivity 入参
     */
    void insertUserTakeActivity(UserTakeActivity userTakeActivity);

    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     * 查询此活动ID，用户最早领取但未消费的一条记录【这部分一般会有业务流程限制，比如是否处理最先还是最新领取单，要根据自己的业务实际场景进行处理】
     *
     * @param userTakeActivity 请求入参
     * @return                 领取结果
     */
    @DBRouter
    UserTakeActivity queryNoConsumedTakeActivityOrder(UserTakeActivity userTakeActivity);

    /**
     * 锁定活动领取记录
     *
     * @param userTakeActivity  入参
     * @return                  更新结果
     */
    int lockTackActivity(UserTakeActivity userTakeActivity);
}
