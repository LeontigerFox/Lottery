package com.banana69.lottery.domain.rule.respository;

import com.banana69.lottery.domain.rule.model.aggregates.TreeRuleRich;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/14:06
 * @description: 规则信息仓储服务接口
 */
public interface IRuleRepository {

    /**
     * 查询规则决策树配置
     *
     * @param treeId    决策树ID
     * @return          决策树配置
     */
    TreeRuleRich queryTreeRuleRich(Long treeId);

}
