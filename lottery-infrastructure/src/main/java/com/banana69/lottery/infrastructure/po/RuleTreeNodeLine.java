package com.banana69.lottery.infrastructure.po;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/14:48
 * @description: 规则树节点连线
 */
@Data
public class RuleTreeNodeLine {
    /** 主键ID */
    private Long id;
    /** 规则树ID */
    private Long treeId;
    /** 节点From */
    private Long nodeIdFrom;
    /** 节点To */
    private Long nodeIdTo;
    /** 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围] */
    private Integer ruleLimitType;
    /** 限定值 */
    private String ruleLimitValue;

}
