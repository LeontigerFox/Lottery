package com.banana69.lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/15/18:12
 * @description: 用户活动参与次数表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTakeActivityCount {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String uId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 可领取次数
     */
    private Integer totalCount;
    /**
     * 已领取次数
     */
    private Integer leftCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }


}
