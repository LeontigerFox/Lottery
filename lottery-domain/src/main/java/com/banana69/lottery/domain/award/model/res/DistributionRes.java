package com.banana69.lottery.domain.award.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/11/20:41
 * @description: 商品配送结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionRes {

    /** 用户ID */
    private String uId;
    /** 编码 */
    private Integer code;
    /** 描述 */
    private String info;

    /** 结算单ID，如：发券后有券码、发货后有单号等，用于存根查询 */
    private String statementId;

    /**
     * 构造函数
     *
     * @param uId   用户ID
     * @param code  编码
     * @param info  描述
     */
    public DistributionRes(String uId, Integer code, String info) {
        this.uId = uId;
        this.code = code;
        this.info = info;
    }



}
