package com.stock.trade.order.mq.producer;

import com.stock.trade.common.mq.producer.AbstractStreamProducer;
import com.stock.trade.order.mq.message.OrderMessage;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer extends AbstractStreamProducer<OrderMessage> {
}