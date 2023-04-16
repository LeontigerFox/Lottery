package com.banana69.lottery.application.process;

import com.banana69.lottery.application.process.req.DrawProcessReq;
import com.banana69.lottery.application.process.res.DrawProcessResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/16/15:12
 * @description: 活动抽奖流程编排接口
 */
public interface IActivityProcess {
    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

}
