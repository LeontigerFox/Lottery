package com.banana69.lottery.domain.strategy.service.draw;

import com.banana69.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.banana69.lottery.domain.strategy.respository.IStrategyRepository;
import com.banana69.lottery.infrastructure.po.Award;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/03/14:18
 * @description: 抽奖策略数据支撑，通用数据服务
 */
public class DrawStrategySupport extends DrawConfig{
    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     * 查询策略配置信息
     * @param strategyId 策略ID
     * @return 策略配置信息
     */
    protected StrategyRich queryStrategyRich(Long strategyId){
        return strategyRepository.queryStrategyRich(strategyId);
    }

    /**
     * 查询奖品详情信息
     * @param awardId
     * @return 中奖详情
     */
    protected Award queryAwardInfoByAwardId(String awardId){
        return strategyRepository.queryAwardInfo(awardId);
    }
}
