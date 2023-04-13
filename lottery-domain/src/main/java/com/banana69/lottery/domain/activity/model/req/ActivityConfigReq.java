package com.banana69.lottery.domain.activity.model.req;

import com.banana69.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/12/14:26
 * @description: 活动配置请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityConfigReq {
    /** 活动ID */
    private Long activityId;

    /** 活动配置信息 */
    private ActivityConfigRich activityConfigRich;

}
