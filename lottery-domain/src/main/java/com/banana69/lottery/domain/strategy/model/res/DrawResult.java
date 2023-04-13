package com.banana69.lottery.domain.strategy.model.res;

import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.strategy.model.vo.DrawAwardInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/12/13:36
 * @description: 抽奖结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawResult {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖 Constants.DrawState
     */
    private Integer drawState = Constants.DrawState.FAIL.getCode();

    /**
     * 中奖奖品信息
     */
    private DrawAwardInfo drawAwardInfo;


    public DrawResult(String uId, Long strategyId, Integer drawState) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.drawState = drawState;
    }

}
