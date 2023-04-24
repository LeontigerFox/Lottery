package com.banana69.lottery.domain.rule.model.aggregates;

import com.banana69.lottery.domain.rule.model.vo.TreeNodeVO;
import com.banana69.lottery.domain.rule.model.vo.TreeRootVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/13:35
 * @description: 规则树聚合
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeRuleRich {

    /** 树根信息 */
    private TreeRootVO treeRoot;
    /** 树节点ID -> 子节点 */
    private Map<Long, TreeNodeVO> treeNodeMap;

    public TreeRootVO getTreeRoot() {
        return treeRoot;
    }


}
