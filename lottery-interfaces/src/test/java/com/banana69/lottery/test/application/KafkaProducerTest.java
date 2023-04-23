package com.banana69.lottery.test.application;

import com.banana69.lottery.application.mq.KafkaProducer;
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
 * @date: 2023/04/23/14:37
 * @description:
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class KafkaProducerTest {
    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    public void test_send() throws InterruptedException {
        // 循环发送消息
       for(int i = 0; i < 5; i++) {
            kafkaProducer.send("你好，我是Lottery --001");
            Thread.sleep(3500);
        }
    }
}
