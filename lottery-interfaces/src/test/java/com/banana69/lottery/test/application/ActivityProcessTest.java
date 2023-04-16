package com.banana69.lottery.test.application;

import com.alibaba.fastjson.JSON;
import com.banana69.lottery.application.process.IActivityProcess;
import com.banana69.lottery.application.process.req.DrawProcessReq;
import com.banana69.lottery.application.process.res.DrawProcessResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/16/15:45
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest

public class ActivityProcessTest {

    @Resource
    private IActivityProcess activityProcess;

    @Test
    public void test_doDrawProcess() {
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("test_uid");
        req.setActivityId(100001L);
        int i = 0;
        while(i < 3)
        {
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);
            log.info("请求入参：{}", JSON.toJSONString(req));
            log.info("测试结果：{}", JSON.toJSONString(drawProcessResult));
            i++;
        }
    }

}
