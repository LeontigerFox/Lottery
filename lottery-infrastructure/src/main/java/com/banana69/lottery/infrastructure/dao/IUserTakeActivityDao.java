package com.banana69.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
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
public interface IUserTakeActivityDao  {

    /**
     * 插入用户领取活动信息
     *
     * @param userTakeActivity 入参
     */
    @DBRouter(key = "uId")
    void insertUserTakeActivity(UserTakeActivity userTakeActivity);

}
