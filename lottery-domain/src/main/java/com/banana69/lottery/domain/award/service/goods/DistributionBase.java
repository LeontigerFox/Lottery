package com.banana69.lottery.domain.award.service.goods;

import com.banana69.lottery.domain.award.model.res.DistributionRes;
import com.banana69.lottery.domain.award.repository.IOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/11/20:35
 * @description: 配送货物基础共用类
 */
public class DistributionBase {

    protected Logger logger = LoggerFactory.getLogger(DistributionRes.class);

    @Resource
    private IOrderRepository awardRepository;

    protected void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {        // 添加更新分库分表中，用户个人的抽奖记录表中奖品发奖状态
        awardRepository.updateUserAwardState(uId, orderId, awardId, grantState);

    }

}
