package com.banana69.lottery.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/15/17:24
 * @description: 活动账单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBillVO {

    /** 用户ID */
    private String uId;

    /** activity 活动ID */
    private Long activityId;
    /** activity 活动名称 */
    private String activityName;
    /** activity 开始时间 */
    private Date beginDateTime;
    /** activity 结束时间 */
    private Date endDateTime;
    /** activity 库存剩余 */
    private Integer stockSurplusCount;
    /**
     * activity 活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启
     * Constants.ActivityState
     */
    private Integer state;
    /** activity 策略ID */
    private Long strategyId;
    /** activity 每人可参与次数 */
    private Integer takeCount;

    /** user_take_activity_count 已领取次数 */
    private Integer userTakeLeftCount;


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
