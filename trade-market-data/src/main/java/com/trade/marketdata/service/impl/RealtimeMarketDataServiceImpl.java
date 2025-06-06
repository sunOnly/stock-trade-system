package com.trade.marketdata.service.impl;

import com.trade.marketdata.entity.RealtimeMarketData;
import com.trade.marketdata.mapper.RealtimeMarketDataMapper;
import com.trade.marketdata.service.RealtimeMarketDataService;
import com.trade.marketdata.util.TushareApi;
import com.trade.marketdata.util.TushareRequest;
import com.trade.marketdata.util.TushareResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 股票实时行情数据 Service 实现类
 *
 * @author Trae
 * @since 2024-07-26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RealtimeMarketDataServiceImpl implements RealtimeMarketDataService {

    private final RealtimeMarketDataMapper realtimeMarketDataMapper;
    private final TushareApi tushareApi;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 获取指定股票代码列表的实时行情数据
     *
     * @param tsCodes 股票代码列表，逗号分隔
     * @return 实时行情数据列表
     */
    @Override
    public List<RealtimeMarketData> getRealtimeMarketData(String tsCodes) {
        TushareRequest<Map<String, String>> request = new TushareRequest<>();
        request.setApiName("realtime_quotes"); // Tushare 实时行情接口名称，请根据实际情况调整
        Map<String, String> params = new HashMap<>();
        params.put("ts_code", tsCodes);
        request.setParams(params);

        TushareResponse<List<List<Object>>> response = tushareApi.call(request, List.class, List.class, Object.class);

        List<RealtimeMarketData> resultList = new ArrayList<>();
        if (response != null && response.getData() != null && response.getData().getItems() != null) {
            List<String> fields = response.getData().getFields();
            List<List<Object>> items = response.getData().getItems();

            for (List<Object> item : items) {
                RealtimeMarketData data = new RealtimeMarketData();
                for (int i = 0; i < fields.size(); i++) {
                    String fieldName = fields.get(i);
                    Object value = item.get(i);
                    if (value == null) continue;

                    switch (fieldName) {
                        case "ts_code":
                            data.setTsCode(String.valueOf(value));
                            break;
                        case "name":
                            data.setName(String.valueOf(value));
                            break;
                        case "price":
                            data.setPrice(new BigDecimal(String.valueOf(value)));
                            break;
                        case "change":
                            data.setChange(new BigDecimal(String.valueOf(value)));
                            break;
                        case "pct_chg": // Tushare返回的字段名可能为 pct_chg
                        case "pct_change":
                            data.setPctChange(new BigDecimal(String.valueOf(value)));
                            break;
                        case "volume":
                            data.setVolume(Long.parseLong(String.valueOf(value)));
                            break;
                        case "amount":
                            data.setAmount(new BigDecimal(String.valueOf(value)));
                            break;
                        case "open":
                            data.setOpen(new BigDecimal(String.valueOf(value)));
                            break;
                        case "pre_close":
                            data.setPreClose(new BigDecimal(String.valueOf(value)));
                            break;
                        case "high":
                            data.setHigh(new BigDecimal(String.valueOf(value)));
                            break;
                        case "low":
                            data.setLow(new BigDecimal(String.valueOf(value)));
                            break;
                        case "time": // Tushare返回的时间字段名可能为 time
                            // 假设Tushare返回的时间格式是 yyyyMMddHHmmss
                            // 如果是其他格式，需要调整 DateTimeFormatter
                            // 如果Tushare直接返回的是 HH:mm:ss 格式，需要结合当前日期进行转换
                            // 这里假设返回的是包含日期的完整时间字符串
                            try {
                                data.setTimestamp(LocalDateTime.parse(String.valueOf(value), FORMATTER));
                            } catch (Exception e) {
                                log.warn("Failed to parse timestamp: {} for ts_code: {}. Error: {}", value, data.getTsCode(), e.getMessage());
                                // 可以设置一个默认值或者根据业务需求处理
                                data.setTimestamp(LocalDateTime.now());
                            }
                            break;
                        default:
                            break;
                    }
                }
                resultList.add(data);
            }
        }
        return resultList;
    }

    /**
     * 保存实时行情数据列表
     *
     * @param realtimeMarketDataList 实时行情数据列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRealtimeMarketData(List<RealtimeMarketData> realtimeMarketDataList) {
        if (realtimeMarketDataList == null || realtimeMarketDataList.isEmpty()) {
            return;
        }
        // 实际应用中，可能需要根据 ts_code 和 timestamp 判断数据是否已存在，进行更新或插入操作
        // 这里简化为直接批量插入
        realtimeMarketDataList.forEach(realtimeMarketDataMapper::insert);
        log.info("Successfully saved {} realtime market data records.", realtimeMarketDataList.size());
    }
}