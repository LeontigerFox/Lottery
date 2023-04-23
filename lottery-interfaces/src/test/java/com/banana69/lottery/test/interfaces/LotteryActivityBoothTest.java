package com.banana69.lottery.test.interfaces;

import com.alibaba.fastjson.JSON;
import com.banana69.lottery.rpc.ILotteryActivityBooth;
import com.banana69.lottery.rpc.req.DrawReq;
import com.banana69.lottery.rpc.req.QuantificationDrawReq;
import com.banana69.lottery.rpc.res.DrawRes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/19/16:50
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LotteryActivityBoothTest {

    @Autowired
    private ILotteryActivityBooth lotteryActivityBooth;

    @Test
    public void test_doDraw() {
        DrawReq drawReq = new DrawReq();
        drawReq.setUId("admin");
        drawReq.setActivityId(100001L);
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        log.info("请求参数：{}", JSON.toJSONString(drawReq));
        log.info("测试结果：{}", JSON.toJSONString(drawRes));
    }

    @Test
    public void testdoQuantificationDraw(){
        QuantificationDrawReq req = new QuantificationDrawReq();
        req.setuId("admin");
        req.setTreeId(2110081902L);
        req.setValMap(new HashMap<String,Object>(){{
            put("gender","man");
            put("age","18");
        }});
        DrawRes drawRes = lotteryActivityBooth.doQuantificationDraw(req);
        log.info("请求参数：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(drawRes));

    }

}
