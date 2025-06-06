package com.trade.marketdata.service;

import com.trade.marketdata.config.TushareConfig;
import com.trade.marketdata.entity.TushareRequest;
import com.trade.marketdata.entity.TushareResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author mac
 * @date 2024/7/16
 * @description Tushare API 调用服务
 */
@Service
public class TushareApi {

    private final WebClient tushareWebClient;
    private final TushareConfig tushareConfig;

    public TushareApi(WebClient tushareWebClient, TushareConfig tushareConfig) {
        this.tushareWebClient = tushareWebClient;
        this.tushareConfig = tushareConfig;
    }

    /**
     * 调用 Tushare API 获取数据
     * @param apiName API 名称，例如 "daily"
     * @param params 请求参数，JSON 格式
     * @param fields 返回字段，逗号分隔
     * @return TushareResponse 响应对象
     */
    public Mono<TushareResponse> post(String apiName, Object params, String fields) {
        TushareRequest request = new TushareRequest();
        request.setApiName(apiName);
        request.setToken(tushareConfig.getToken());
        request.setParams(params);
        request.setFields(fields);

        return tushareWebClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TushareResponse.class);
    }
}