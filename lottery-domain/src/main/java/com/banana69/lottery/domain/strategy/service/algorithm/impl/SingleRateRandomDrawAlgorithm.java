package com.banana69.lottery.domain.strategy.service.algorithm.impl;

import com.banana69.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.banana69.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/3/18:15
 * @description: 单项随机概率抽奖，抽到一个已经排掉的奖品则未中奖
 */
@Component("singleRateRandomDrawAlgorithm")
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        // 获取策略对应的元祖
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        assert rateTuple != null;

        // 随机索引
        int randomVal = this.generateSecureRandomIntCode(100);
        int idx = super.hashIdx(randomVal);

        // 返回结果
        String awardId = rateTuple[idx];

        // 如果中奖ID命中排除奖品列表，则返回NULL
        if (excludeAwardIds.contains(awardId)) {
            return null;
        }
        return awardId;
    }
}
