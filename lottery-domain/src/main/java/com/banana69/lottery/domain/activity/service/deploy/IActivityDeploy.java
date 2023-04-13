package com.banana69.lottery.domain.activity.service.deploy;

import com.banana69.lottery.domain.activity.model.req.ActivityConfigReq;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/14:29
 * @description: 部署活动配置接口
 */
public interface IActivityDeploy {
    /**
     * 创建活动信息
     *
     * @param req 活动配置信息
     */
    void createActivity(ActivityConfigReq req);

    /**
     * 修改活动信息
     *
     * @param req 活动配置信息
     */
    void updateActivity(ActivityConfigReq req);

}
