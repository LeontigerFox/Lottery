package com.banana69.lottery.application.process.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/16/15:15
 * @description: 抽奖请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawProcessReq {
    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
