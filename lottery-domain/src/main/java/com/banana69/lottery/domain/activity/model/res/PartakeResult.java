package com.banana69.lottery.domain.activity.model.res;

import com.banana69.lottery.common.Result;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/15/17:22
 * @description: 活动参与结果
 */
public class PartakeResult  extends Result {
    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 活动领取ID
     */
    private Long takeId;


    public PartakeResult(String code, String info) {
        super(code, info);
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Long getTakeId() {
        return takeId;
    }

    public void setTakeId(Long takeId) {
        this.takeId = takeId;
    }




}
