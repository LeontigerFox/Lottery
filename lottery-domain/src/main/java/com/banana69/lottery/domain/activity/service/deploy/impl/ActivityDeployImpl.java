package com.banana69.lottery.domain.activity.service.deploy.impl;

import com.alibaba.fastjson.JSON;
import com.banana69.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import com.banana69.lottery.domain.activity.model.req.ActivityConfigReq;
import com.banana69.lottery.domain.activity.model.vo.ActivityVO;
import com.banana69.lottery.domain.activity.model.vo.AwardVO;
import com.banana69.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.banana69.lottery.domain.activity.model.vo.StrategyVO;
import com.banana69.lottery.domain.activity.respository.IActivityRepository;
import com.banana69.lottery.domain.activity.service.deploy.IActivityDeploy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/14:30
 * @description: 部署活动配置服务
 */
@Service
public class ActivityDeployImpl implements IActivityDeploy {

    private Logger logger = LoggerFactory.getLogger(ActivityDeployImpl.class);

    @Resource
    private IActivityRepository activityRepository;


    @Override
    public void createActivity(ActivityConfigReq req) {
        logger.info("创建活动配置开始，activityId：{}", req.getActivityId());
        ActivityConfigRich activityConfigRich = req.getActivityConfigRich();

        try {
            // 添加活动配置
            ActivityVO activity = activityConfigRich.getActivity();
            activityRepository.addActivity(activity);

            // 添加奖品配置
            List<AwardVO> awardList = activityConfigRich.getAwardList();
            activityRepository.addAward(awardList);

            // 添加策略配置
            StrategyVO strategy = activityConfigRich.getStrategy();
            activityRepository.addStrategy(strategy);

            // 添加策略明细配置
            List<StrategyDetailVO> strategyDetailList = activityConfigRich.getStrategy().getStrategyDetailList();
            activityRepository.addStrategyDetailList(strategyDetailList);

            logger.info("创建活动配置完成，activityId：{}", req.getActivityId());


        } catch (DuplicateKeyException e){
            logger.error("创建活动配置失败，唯一索引冲突 activityId：{} reqJson：{}", req.getActivityId(), JSON.toJSONString(req), e);
            throw e;

        }

    }

    @Override
    public void updateActivity(ActivityConfigReq req) {
        // TODO: 非核心功能后续补充
    }

}
