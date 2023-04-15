package com.banana69.lottery.test.domain;

import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.support.ids.IIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/13/17:14
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupportTest {

    private Logger logger = LoggerFactory.getLogger(SupportTest.class);

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Test
    public void test_ids(){
        logger.info("雪花算法策略，生成ID：{}", idGeneratorMap.get(Constants.Ids.SnowFlake).nextId());
        logger.info("日期算法策略，生成ID：{}", idGeneratorMap.get(Constants.Ids.ShortCode).nextId());
        logger.info("随机算法策略，生成ID：{}", idGeneratorMap.get(Constants.Ids.RandomNumeric).nextId());

    }

}
