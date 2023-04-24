package com.banana69.lottery.test.application;

import com.banana69.lottery.application.mq.producer.KafkaProducer;
import com.banana69.lottery.common.Constants;
import com.banana69.lottery.domain.activity.model.vo.InvoiceVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/23/18:35
 * @description: 消息测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class KafkaProducerTest {

    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    public void test_send() throws InterruptedException {
        InvoiceVO invoice = new InvoiceVO();
        invoice.setuId("admin");
        invoice.setOrderId(1444540456057864192L);
        invoice.setAwardId("3");
        invoice.setAwardType(Constants.AwardType.DESC.getCode());
        invoice.setAwardName("Code");
        invoice.setAwardContent("苹果电脑");
        invoice.setShippingAddress(null);
        invoice.setExtInfo(null);
        kafkaProducer.sendLotteryInvoice(invoice);
        while (true){
            Thread.sleep(10000);
        }
    }

}
