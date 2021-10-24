package com.util.http;

import com.util.http.strategy.HttpClientStrategy;
import com.util.http.strategy.OkHttpStrategy;
import com.util.http.strategy.RestTemplateStrategy;
import com.util.http.strategy.WebClientStrategy;

public enum StrategyType {

    STRATEGY_OK_HTTP("OkHttp", new OkHttpStrategy()),

    STRATEGY_REST_TEMPLATE("RestTemplate", new RestTemplateStrategy()),

    STRATEGY_HTTP_CLIENT("HttpClient", new HttpClientStrategy()),

    STRATEGY_WEB_CLIENT("WebClient", new WebClientStrategy());

    /**
     * 策略标识
     */
    String code;

    /**
     * 策略
     */
    BaseWorkStrategy strategy;

    StrategyType(String code, BaseWorkStrategy strategy) {
        this.code = code;
        this.strategy = strategy;
    }
}
