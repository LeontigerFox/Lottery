package com.banana69.lottery.test.domain;

import com.alibaba.fastjson.JSON;
import com.banana69.lottery.domain.rule.model.req.DecisionMatterReq;
import com.banana69.lottery.domain.rule.model.res.EngineResult;
import com.banana69.lottery.domain.rule.service.engine.EngineFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;



import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/18/15:03
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RuleTest {
    @Resource
    private EngineFilter engineFilter;

    @Test
    public void test_process() {
        DecisionMatterReq req = new DecisionMatterReq();
        req.setTreeId(2110081902L);
        req.setUserId("fustack");
        req.setValMap(new HashMap<String, Object>() {{
            put("gender", "man");
            put("age", "25");
        }});

        EngineResult res = engineFilter.process(req);

        log.info("请求参数：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(res));
    }

}
