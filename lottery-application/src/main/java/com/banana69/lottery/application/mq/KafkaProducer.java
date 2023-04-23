package com.banana69.lottery.application.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/23/14:30
 * @description: 消息生产者
 */
@Component
@Slf4j
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_TEST = "Hello-Kafka";

    public static final String TOPIC_GROUP = "test-consumer-group";


    public void send(Object obj){
        String obj2String = JSON.toJSONString(obj);
        log.info("准备发送消息为：{}", obj2String);

        // 发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_TEST, obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable e) {
                // 发送失败
                log.error(TOPIC_TEST + " - 生产者 发送消息失败：" + e.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                // 发送成功
                log.info(TOPIC_TEST + " - 生产者 发送消息成功：" + result.toString());
            }
        });
    }

}
