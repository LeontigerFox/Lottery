package com.banana69.lottery.domain.rule.service.logic.impl;

import com.banana69.lottery.domain.rule.model.req.DecisionMatterReq;
import com.banana69.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/14:17
 * @description: 性别规则
 */
@Component
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
}
