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
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        StrategyType strategyType = getStrategyType();
        return strategyType.strategy.doGet(url, headers, params);
    }

    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        StrategyType strategyType = getStrategyType();
        return strategyType.strategy.doPost(url, headers, params);
    }

    @Override
    public void doGetAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        StrategyType strategyType = getStrategyType();
        strategyType.strategy.doGetAsync(url, headers, params);
    }

    @Override
    public void doPostAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        StrategyType strategyType = getStrategyType();
        strategyType.strategy.doPostAsync(url, headers, params);
    }
}
