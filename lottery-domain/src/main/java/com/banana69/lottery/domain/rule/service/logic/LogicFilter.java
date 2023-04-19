package com.banana69.lottery.domain.rule.service.logic;

import com.banana69.lottery.domain.rule.model.req.DecisionMatterReq;
import com.banana69.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/14:07
 * @description: 规则过滤器接口
 */
public interface LogicFilter {
    /**
     * 逻辑决策器
     * @param matterValue          决策值
     * @param treeNodeLineInfoList 决策节点
     * @return                     下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList);

    /**
     * 获取决策值
     * @param decisionMatter
     * @return
     */
    String matterValue(DecisionMatterReq decisionMatter);
}
