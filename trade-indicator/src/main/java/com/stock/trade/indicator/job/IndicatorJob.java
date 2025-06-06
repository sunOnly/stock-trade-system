package com.stock.trade.indicator.job;

import com.stock.trade.indicator.service.IndicatorValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 技术指标定时计算任务
 *
 * @author tianxin
 */
@Component
@Slf4j
public class IndicatorJob {

    @Resource
    private IndicatorValueService indicatorValueService;

    /**
     * 定时触发所有启用的技术指标计算
     * TODO 后续可以优化为分布式任务调度，例如 XXL-Job
     */
    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行
    public void execute() {
        log.info("[execute][开始执行技术指标计算任务]");
        try {
            indicatorValueService.triggerAllIndicatorCalculation();
            log.info("[execute][技术指标计算任务执行成功]");
        } catch (Exception e) {
            log.error("[execute][技术指标计算任务执行失败]", e);
        }
    }

}