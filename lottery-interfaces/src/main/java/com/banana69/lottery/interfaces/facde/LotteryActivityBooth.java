package com.banana69.lottery.interfaces.facde;

import com.alibaba.fastjson.JSON;
import com.banana69.lottery.application.process.IActivityProcess;
import com.banana69.lottery.application.process.req.DrawProcessReq;
import com.banana69.lottery.application.process.res.DrawProcessResult;
import com.banana69.lottery.application.process.res.RuleQuantificationCrowdResult;
import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.rule.model.req.DecisionMatterReq;
import com.banana69.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.banana69.lottery.interfaces.assembler.IMapping;
import com.banana69.lottery.rpc.ILotteryActivityBooth;
import com.banana69.lottery.rpc.dto.AwardDTO;
import com.banana69.lottery.rpc.req.DrawReq;
import com.banana69.lottery.rpc.req.QuantificationDrawReq;
import com.banana69.lottery.rpc.res.DrawRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/19/16:35
 * @description: 抽奖活动展台
 */
@Controller
@Slf4j
public class LotteryActivityBooth implements ILotteryActivityBooth {
    @Resource
    private IActivityProcess activityProcess;

    @Resource
    private IMapping<DrawAwardVO, AwardDTO> awardMapping;

    @Override
    public DrawRes doDraw(DrawReq drawReq) {
       try {
           log.info("抽奖，开始 uId：{} activityId：{}", drawReq.getUId(), drawReq.getActivityId());

           // 1. 执行抽奖
           DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getUId(), drawReq.getActivityId()));
           if(!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())){
               log.error("抽奖，失败(抽奖过程异常) uId：{} activityId：{}", drawReq.getUId(), drawReq.getActivityId());
               return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
           }

           // 2. 数据转换
           DrawAwardVO drawAwardVO = drawProcessResult.getDrawAwardVO();
           AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
           awardDTO.setActivityId(drawReq.getActivityId());

           // 3. 封装数据
           DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
           drawRes.setAwardDTO(awardDTO);
           log.info("抽奖，完成 uId：{} activityId：{} drawRes：{}", drawReq.getUId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));

           return drawRes;

       }catch (Exception e) {
           log.error("抽奖，失败 uId：{} activityId：{} reqJson：{}", drawReq.getUId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
           return new DrawRes(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
       }
    }

    @Override
    // TODO 量化人群抽奖细节
    public DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq) {
        try {
            log.info("量化人群抽奖，开始 uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());

            // 1. 执行规则引擎，获取用户可以参与的活动号
            RuleQuantificationCrowdResult ruleQuantificationCrowdResult = activityProcess.doRuleQuantificationCrowd(new DecisionMatterReq(quantificationDrawReq.getuId(),
                    quantificationDrawReq.getTreeId(), quantificationDrawReq.getValMap()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(ruleQuantificationCrowdResult.getCode())) {
                log.error("量化人群抽奖，失败(规则引擎执行异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(ruleQuantificationCrowdResult.getCode(), ruleQuantificationCrowdResult.getInfo());
            }

            // 2. 执行抽奖
            Long activityId = ruleQuantificationCrowdResult.getActivityId();
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(quantificationDrawReq.getuId(), activityId));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                log.error("量化人群抽奖，失败(抽奖过程异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }

            // 3. 数据转换
            DrawAwardVO drawAwardVO = drawProcessResult.getDrawAwardVO();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(activityId);

            // 4. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("量化人群抽奖，完成 uId：{} treeId：{} drawRes：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            log.error("量化人群抽奖，失败 uId：{} treeId：{} reqJson：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(quantificationDrawReq), e);
            return new DrawRes(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

}

