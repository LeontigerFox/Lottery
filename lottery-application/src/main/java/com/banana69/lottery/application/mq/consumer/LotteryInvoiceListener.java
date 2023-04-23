package com.banana69.lottery.application.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.banana69.lottery.domain.activity.model.vo.InvoiceVO;
import com.banana69.lottery.domain.award.model.req.GoodsReq;
import com.banana69.lottery.domain.award.model.res.DistributionRes;
import com.banana69.lottery.domain.award.service.factory.DistributionGoodsFactory;
import com.banana69.lottery.domain.award.service.goods.IDistributionGoods;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/23/17:07
 * @description: 中奖发货单监听消息
 */
@Component
@Slf4j
public class LotteryInvoiceListener {
    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @KafkaListener(topics = "lottery_invoice", groupId = "lottery")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        Optional<?> message = Optional.ofNullable(record.value());

        // 1. 判断消息是否存在
        if(!message.isPresent()){
            return;
        }

        // 2. 处理 MQ 消息
        try {
            // 1. 转化对象
            InvoiceVO invoiceVO = JSON.parseObject((String) message.get(), InvoiceVO.class);

            // 2. 获取发送奖品工厂，执行发奖
            IDistributionGoods distributionGoodsService =  distributionGoodsFactory.getDistributionGoodsService(invoiceVO.getAwardType());
            DistributionRes distributionRes = distributionGoodsService.doDistribution(new GoodsReq(invoiceVO.getuId(), invoiceVO.getOrderId(),
                    invoiceVO.getAwardId(), invoiceVO.getAwardName(), invoiceVO.getAwardContent()));

            // 3. 打印日志
            log.info("消费MQ消息，完成 topic：{} bizId：{} 发奖结果：{}", topic, invoiceVO.getuId(), JSON.toJSONString(distributionRes));

            // 4. 消息消费完成
            ack.acknowledge();
        }catch (Exception e){
            // 发奖环节失败，消息重试。所有到环节，发货、更新库，都需要保证幂等。
            log.error("消费MQ消息，失败 topic：{} message：{}", topic, message.get());
            throw e;

        }
    }


}
