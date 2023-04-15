package com.banana69.lotteryERP.application;

import com.banana69.lotteryERP.domain.activity.model.ActivityListPageReq;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/17:54
 * @description: 活动服务接口
 */
public interface IActivityService {
    /**
     * 查询活动分页数据
     * @param req   入参
     * @return      结果
     */
    EasyResult queryActivityListPage(ActivityListPageReq req);

}
