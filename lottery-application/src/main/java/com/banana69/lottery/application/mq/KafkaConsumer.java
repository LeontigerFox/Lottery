package com.banana69.lottery.application.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/23/14:15
 * @description: 消息消费者
 */
@Component
@Slf4j
public class KafkaConsumer {


    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP)
    public void topicTest(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        Optional<?> message = Optional.ofNullable(record.value());
        if(message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test 消费了： Topic：" + topic + ", message: " + msg);
            ack.acknowledge();
        }
    }

}
