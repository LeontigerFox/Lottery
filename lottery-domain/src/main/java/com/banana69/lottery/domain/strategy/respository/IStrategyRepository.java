package com.banana69.lottery.domain.strategy.respository;

import com.banana69.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.banana69.lottery.domain.strategy.model.vo.AwardBriefVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/12/13:37
 * @description:  策略表仓储服务
 */
public interface IStrategyRepository {

    StrategyRich queryStrategyRich(Long strategyId);

    AwardBriefVO queryAwardInfo(String awardId);

    List<String> queryNoStockStrategyAwardList(Long strategyId);


    /**
     * 扣减库存
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return           扣减结果
     */
    boolean deductStock(Long strategyId, String awardId);


}
