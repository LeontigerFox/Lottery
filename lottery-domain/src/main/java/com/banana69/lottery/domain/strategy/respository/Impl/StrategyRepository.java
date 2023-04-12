package com.banana69.lottery.domain.strategy.respository.Impl;

import com.banana69.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.banana69.lottery.domain.strategy.respository.IStrategyRepository;
import com.banana69.lottery.infrastructure.dao.IAwardDao;
import com.banana69.lottery.infrastructure.dao.IStrategyDao;
import com.banana69.lottery.infrastructure.dao.IStrategyDetailDao;
import com.banana69.lottery.infrastructure.po.Award;
import com.banana69.lottery.infrastructure.po.Strategy;
import com.banana69.lottery.infrastructure.po.StrategyDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/2/00:19
 * @description: 策略表仓储服务
 */
@Component
public class StrategyRepository implements IStrategyRepository{
    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;


    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = strategyDao.selectOne(new QueryWrapper<Strategy>().eq("strategy_id",strategyId));
        List<StrategyDetail> strategyDetailList = strategyDetailDao.selectList(
                new QueryWrapper<StrategyDetail>().eq("strategy_id",strategyId));
        return new StrategyRich(strategyId, strategy, strategyDetailList);
    }

    @Override
    public Award queryAwardInfo(String awardId) {
        return awardDao.selectOne(new QueryWrapper<Award>().eq("award_id",awardId));
    }

    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        return strategyDetailDao.queryNoStockStrategyAwardList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        StrategyDetail req = new StrategyDetail();
        req.setStrategyId(strategyId);
        req.setAwardId(awardId);
        int count = strategyDetailDao.deductStock(req);
        return count == 1;
    }
}
