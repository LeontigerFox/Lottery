package com.banana69.lottery.domain.activity.model.aggregates;

import com.banana69.lottery.domain.activity.model.vo.ActivityVO;
import com.banana69.lottery.domain.activity.model.vo.AwardVO;
import com.banana69.lottery.domain.activity.model.vo.StrategyVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/14:21
 * @description: 活动配置聚合信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityConfigRich {

    /** 活动配置 */
    private ActivityVO activity;

    /** 策略配置(含策略明细) */
    private StrategyVO strategy;

    /** 奖品配置 */
    private List<AwardVO> awardList;



}
