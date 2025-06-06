package com.stock.marketdata.controller;

import com.stock.marketdata.service.MarketDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.marketdata.dal.dataobject.MarketDataDO;
import com.stock.common.pojo.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MarketDataController.class)
class MarketDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketDataService marketDataService;

    @Autowired
    private ObjectMapper objectMapper;

    private MarketDataDO createMarketDataDO() {
        MarketDataDO marketData = new MarketDataDO();
        marketData.setId(1L);
        marketData.setStockCode("000001");
        marketData.setStockName("平安银行");
        marketData.setOpenPrice(BigDecimal.valueOf(10.00));
        marketData.setClosePrice(BigDecimal.valueOf(10.50));
        marketData.setHighPrice(BigDecimal.valueOf(10.60));
        marketData.setLowPrice(BigDecimal.valueOf(9.90));
        marketData.setVolume(10000L);
        marketData.setTurnover(BigDecimal.valueOf(105000.00));
        marketData.setTradeTime(LocalDateTime.now());
        return marketData;
    }

    @Test
    void createMarketData() throws Exception {
        MarketDataDO marketData = createMarketDataDO();
        when(marketDataService.createMarketData(any(MarketDataDO.class))).thenReturn(1L);

        mockMvc.perform(post("/market-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(marketData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value(1L));
    }

    @Test
    void updateMarketData() throws Exception {
        MarketDataDO marketData = createMarketDataDO();
        doNothing().when(marketDataService).updateMarketData(any(MarketDataDO.class));

        mockMvc.perform(put("/market-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(marketData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    void deleteMarketData() throws Exception {
        doNothing().when(marketDataService).deleteMarketData(anyLong());

        mockMvc.perform(delete("/market-data/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    void getMarketData() throws Exception {
        MarketDataDO marketData = createMarketDataDO();
        when(marketDataService.getMarketData(anyLong())).thenReturn(marketData);

        mockMvc.perform(get("/market-data/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.stockCode").value("000001"));
    }

    @Test
    void getMarketDataList() throws Exception {
        MarketDataDO marketData = createMarketDataDO();
        List<MarketDataDO> list = Collections.singletonList(marketData);
        when(marketDataService.getMarketDataList(anyString())).thenReturn(list);

        mockMvc.perform(get("/market-data/list").param("stockCode", "000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].stockCode").value("000001"));
    }

    @Test
    void pageMarketData() throws Exception {
        MarketDataDO marketData = createMarketDataDO();
        Page<MarketDataDO> page = new Page<>(1, 10);
        page.setRecords(Collections.singletonList(marketData));
        page.setTotal(1L);

        when(marketDataService.pageMarketData(any(Page.class), anyString())).thenReturn(page);

        mockMvc.perform(get("/market-data/page")
                .param("pageNum", "1")
                .param("pageSize", "10")
                .param("stockCode", "000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.records[0].id").value(1L))
                .andExpect(jsonPath("$.data.total").value(1L));
    }
}