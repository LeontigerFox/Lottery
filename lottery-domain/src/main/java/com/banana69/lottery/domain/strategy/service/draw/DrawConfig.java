package com.banana69.lottery.domain.strategy.service.draw;

import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author banana69
 * 加载抽奖策略
 */
public class DrawConfig {

    @Resource(name = "entiretyRateRandomDrawAlgorithm")
    private IDrawAlgorithm entiretyRateRandomDrawAlgorithm;

    @Resource(name = "singleRateRandomDrawAlgorithm")
    private IDrawAlgorithm singleRateRandomDrawAlgorithm;

    /** 抽奖策略组 */
    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        drawAlgorithmGroup.put(Constants.StrategyMode.ENTIRETY.getCode(), entiretyRateRandomDrawAlgorithm);
        drawAlgorithmGroup.put(Constants.StrategyMode.SINGLE.getCode(), singleRateRandomDrawAlgorithm);
    }


}
