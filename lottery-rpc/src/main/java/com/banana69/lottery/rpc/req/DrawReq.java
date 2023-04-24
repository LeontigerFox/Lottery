package com.banana69.lottery.rpc.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DrawReq implements Serializable {
    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;

}
