package com.banana69.lotteryERP.domain.activity.service;

import com.banana69.lotteryERP.application.IActivityService;
import com.banana69.lotteryERP.domain.activity.model.ActivityListPageReq;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/18:00
 * @description:
 */
@Component
public class ActivityServiceImpl implements IActivityService {
    @Override
    public EasyResult<Integer> queryActivityListPage(ActivityListPageReq req) {

        return  EasyResult.success(200,"ok");
    }
}
