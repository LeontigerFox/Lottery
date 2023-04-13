package com.banana69.lottery.infrastructure.repository;

import com.banana69.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.banana69.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.banana69.lottery.domain.strategy.model.vo.StrategyBriefVO;
import com.banana69.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import com.banana69.lottery.domain.strategy.respository.IStrategyRepository;
import com.banana69.lottery.infrastructure.dao.IAwardDao;
import com.banana69.lottery.infrastructure.dao.IStrategyDao;
import com.banana69.lottery.infrastructure.dao.IStrategyDetailDao;
import com.banana69.lottery.infrastructure.po.Award;
import com.banana69.lottery.infrastructure.po.Strategy;
import com.banana69.lottery.infrastructure.po.StrategyDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/13:48
 * @description: 策略表仓储服务
 */
@Component
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = strategyDao.selectOne(new QueryWrapper<Strategy>().eq("strategy_id",strategyId));
        List<StrategyDetail> strategyDetailList = strategyDetailDao.selectBatchIds(Collections.singleton(strategyId));

        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        BeanUtils.copyProperties(strategy, strategyBriefVO);

        List<StrategyDetailBriefVO> strategyDetailBriefVOList = new ArrayList<>();
        for(StrategyDetail strategyDetail: strategyDetailList){
            StrategyDetailBriefVO strategyDetailBriefVO = new StrategyDetailBriefVO();
            BeanUtils.copyProperties(strategyDetail, strategyDetailBriefVO);
            strategyDetailBriefVOList.add(strategyDetailBriefVO);
        }

        return new StrategyRich(strategyId, strategyBriefVO, strategyDetailBriefVOList);
    }

    @Override
    public AwardBriefVO queryAwardInfo(String awardId) {

        Award award = awardDao.selectById(new QueryWrapper<Award>().eq("award_id", awardId));

        // 可以使用 BeanUtils.copyProperties(award, awardBriefVO)、或者基于ASM实现的Bean-Mapping，但在效率上最好的依旧是硬编码
        AwardBriefVO awardBriefVO = new AwardBriefVO();
        awardBriefVO.setAwardId(award.getAwardId());
        awardBriefVO.setAwardType(award.getAwardType());
        awardBriefVO.setAwardName(award.getAwardName());
        awardBriefVO.setAwardContent(award.getAwardContent());

        return awardBriefVO;
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
