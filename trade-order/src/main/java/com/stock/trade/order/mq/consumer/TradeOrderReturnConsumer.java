package com.stock.trade.order.mq.consumer;

import com.stock.trade.order.service.OrderService;
import com.stock.trade.tradeengine.message.TradeOrderReturnMessage; // 假设成交回报消息定义在 trade-engine 模块
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 订单成交回报消息消费者
 */
@Component
@Slf4j
public class TradeOrderReturnConsumer {

    @Resource
    private OrderService orderService;

    @EventListener
    public void onMessage(TradeOrderReturnMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        try {
            orderService.processOrderReturn(message);
        } catch (Throwable e) {
            log.error("[onMessage][处理订单成交回报({}) 异常]", message, e);
            // TODO: 考虑增加重试机制或死信队列
        }
    }

}