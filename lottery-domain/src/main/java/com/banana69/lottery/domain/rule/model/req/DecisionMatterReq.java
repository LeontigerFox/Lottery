package com.banana69.lottery.domain.rule.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/14:03
 * @description: 决策物料
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecisionMatterReq {

    /**
     * 规则树ID
     */
    private Long treeId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 决策值
     */
    private Map<String, Object> valMap;


    public DecisionMatterReq(String userId, Long treeId, Map<String, Object> valMap) {
        this.userId = userId;
        this.treeId = treeId;
        this.valMap = valMap;
    }




}
