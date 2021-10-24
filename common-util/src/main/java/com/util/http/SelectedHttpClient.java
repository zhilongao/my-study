package com.util.http;


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






    //hutool
    // 1. 创建执行的Call对象
    // 2. 调用Call的execute方法

    // 执行的相关组件


    /********************************************* OkHttp **********************************/
//    public String okHttpGet(String url, Map<String, String> headers, Map<String, Object> params) {
//        return okHttpStrategy.doGet(url, headers, params);
//    }
//
//    public String okHttpPost(String url, Map<String, String> headers, Map<String, Object> body) {
//        return okHttpStrategy.doPost(url, headers, body);
//    }

    /********************************************* RestTemplateGet **********************************/
//    public String restTemplateGet(String url, Map<String, String> headers, Map<String, Object> params){
//        return restTemplateStrategy.doGet(url, headers, params);
//    }
//
//    public String restTemplatePost(String url, Map<String, String> headers, Map<String, Object> params) {
//        return restTemplateStrategy.doPost(url, headers, params);
//    }
//
//    /********************************************* HttpClient **********************************/
//    public String httpClientGet(String url, Map<String, String> headers, Map<String, Object> params) {
//       return httpClientStrategy.doGet(url, headers, params);
//    }
//
//    public String httpClientPost(String url, Map<String, String> headers, Map<String, Object> params) {
//        return httpClientStrategy.doPost(url, headers, params);
//    }
//
//
//    public String webClientGet(String url, Map<String, String> headers, Map<String, Object> params) {
//        return webClientStrategy.doGet(url, headers, params);
//    }
//
//    public String webClientPost(String url, Map<String, String> headers, Map<String, Object> params) {
//        return webClientStrategy.doPost(url, headers, params);
//    }
}
