package com.banana69.lotteryERP.interfaces.lottery.service.impl;

import com.banana69.lotteryERP.domain.activity.model.ActivityListPageReq;
import com.banana69.lotteryERP.infrastrusture.common.EasyResult;
import com.banana69.lotteryERP.interfaces.lottery.dto.Activity;
import com.banana69.lotteryERP.interfaces.lottery.mapper.ActivityMapper;
import com.banana69.lotteryERP.interfaces.lottery.service.IActivityService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/24/10:55
 * @description:
 */
@Service
@DS("lottery")
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    @Override
    public EasyResult<?> queryActivityListPage(ActivityListPageReq req) {
        return EasyResult.success("Success!");
    }
}
