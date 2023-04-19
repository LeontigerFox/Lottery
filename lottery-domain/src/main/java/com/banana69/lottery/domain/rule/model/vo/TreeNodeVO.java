package com.banana69.lottery.domain.rule.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/13:37
 * @description: 规则树节点信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeVO {
    /** 规则树ID */
    private Long treeId;
    /** 规则树节点ID */
    private Long treeNodeId;
    /** 节点类型；1子叶、2果实 */
    private Integer nodeType;
    /** 节点值[nodeType=2]；果实值 */
    private String nodeValue;
    /** 规则Key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 节点链路 */
    private List<TreeNodeLineVO> treeNodeLineInfoList;

}
