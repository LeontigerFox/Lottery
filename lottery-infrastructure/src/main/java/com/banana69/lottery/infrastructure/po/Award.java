package com.banana69.lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author banana69
 * 奖品明细
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award {

    /** 自增ID */
    private Long id;

    /** 奖品ID */
    private String awardId;

    /** 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品） */
    private Integer awardType;

    ///** 奖品数量 */
    //private Integer awardCount;

    /** 奖品名称 */
    private String awardName;

    /** 奖品内容「描述、奖品码、sku」 */
    private String awardContent;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;


}
