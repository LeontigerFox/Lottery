package com.banana69.lottery.infrastructure.repository;

import cn.hutool.core.bean.BeanUtil;
import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.activity.model.vo.*;
import com.banana69.lottery.domain.activity.respository.IActivityRepository;
import com.banana69.lottery.infrastructure.dao.IActivityDao;
import com.banana69.lottery.infrastructure.dao.IAwardDao;
import com.banana69.lottery.infrastructure.dao.IStrategyDao;
import com.banana69.lottery.infrastructure.dao.IStrategyDetailDao;
import com.banana69.lottery.infrastructure.po.Activity;
import com.banana69.lottery.infrastructure.po.Award;
import com.banana69.lottery.infrastructure.po.Strategy;
import com.banana69.lottery.infrastructure.po.StrategyDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/15:11
 * @description:
 */
@Component
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IAwardDao awardDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Override
    public void addActivity(ActivityVO activity) {
        Activity req = new Activity();
        BeanUtil.copyProperties(activity, req);
        activityDao.insert(req);
    }

    @Override
    public void addAward(List<AwardVO> awardList) {
        List<Award> req = new ArrayList<Award>();
        for(AwardVO awardVO: awardList){
            Award award = new Award();
            BeanUtil.copyProperties(awardVO, award);
            req.add(award);
        }
        awardDao.insertList(req);
    }

    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy, req);
        strategyDao.insert(req);

    }

    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {
        List<StrategyDetail> req = new ArrayList<>();
        for (StrategyDetailVO strategyDetailVO : strategyDetailList) {
            StrategyDetail strategyDetail = new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            req.add(strategyDetail);
        }
        strategyDetailDao.insertList(req);

    }

    @Override
    public boolean alterStatus(Long activityId, Enum<Constants.ActivityState> beforeState, Enum<Constants.ActivityState> afterState) {
        AlterStateVO alterStateVO = new AlterStateVO(activityId,((Constants.ActivityState) beforeState).getCode(),((Constants.ActivityState) afterState).getCode());
        Activity activity = new Activity();
        BeanUtils.copyProperties(alterStateVO,activity);
        int count = activityDao.updateById(activity);
        return 1 == count;
    }
}
