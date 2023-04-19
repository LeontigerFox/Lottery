package com.banana69.lottery.domain.rule.service.engine;

import com.banana69.lottery.domain.rule.model.req.DecisionMatterReq;
import com.banana69.lottery.domain.rule.model.res.EngineResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/14:19
 * @description: 规则过滤器引擎
 */
public interface EngineFilter {
    /**
     * 规则过滤器接口
     *
     * @param matter      规则决策物料
     * @return            规则决策结果
     */
    EngineResult process(final DecisionMatterReq matter);

}
