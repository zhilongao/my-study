package com.util.http;


import com.util.http.strategy.BaseWorkStrategy;

import java.util.Map;

/**
 * 使用OkHttp发送Post和Get请求
 */
public class SelectedHttpClient extends BaseWorkStrategy {

    private StrategyType strategyType = StrategyType.STRATEGY_HTTP_CLIENT;


    public StrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    protected String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        StrategyType strategyType = getStrategyType();
        return strategyType.strategy.doGet(url, headers, params);
    }

    @Override
    protected String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        StrategyType strategyType = getStrategyType();
        return strategyType.strategy.doPost(url, headers, params);
    }
}
