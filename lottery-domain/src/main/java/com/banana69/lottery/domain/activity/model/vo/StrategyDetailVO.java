package com.banana69.lottery.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/14:24
 * @description: 策略详情配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyDetailVO {

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品库存
     */
    private Integer awardCount;

    /**
     * 奖品剩余库存
     */
    private Integer awardSurplusCount;

    /**
     * 中奖概率
     */
    private BigDecimal awardRate;

    @Override
    public String toString() {
        return "StrategyDetailVO{" +
                "strategyId=" + strategyId +
                ", awardId='" + awardId + '\'' +
                ", awardName='" + awardName + '\'' +
                ", awardCount=" + awardCount +
                ", awardSurplusCount=" + awardSurplusCount +
                ", awardRate=" + awardRate +
                '}';
    }


}
