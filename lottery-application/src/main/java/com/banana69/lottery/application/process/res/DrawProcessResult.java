package com.banana69.lottery.application.process.res;

import com.banana69.lottery.common.Result;
import com.banana69.lottery.domain.strategy.model.vo.DrawAwardInfo;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/16/15:13
 * @description: 活动抽奖结果
 */
public class DrawProcessResult extends Result {

    private DrawAwardInfo  drawAwardInfo;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardInfo drawAwardInfo) {
        super(code, info);
        this.drawAwardInfo = drawAwardInfo;
    }

    public DrawAwardInfo getDrawAwardInfo() {
        return drawAwardInfo;
    }

    public void setDrawAwardInfo(DrawAwardInfo drawAwardInfo) {
        this.drawAwardInfo = drawAwardInfo;
    }

}
