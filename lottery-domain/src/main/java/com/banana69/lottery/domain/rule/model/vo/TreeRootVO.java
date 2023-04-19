package com.banana69.lottery.domain.rule.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/13:39
 * @description: 规则树根配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeRootVO {
    /** 规则树ID */
    private Long treeId;
    /** 规则树根ID */
    private Long treeRootNodeId;
    /** 规则树名称 */
    private String treeName;

}
