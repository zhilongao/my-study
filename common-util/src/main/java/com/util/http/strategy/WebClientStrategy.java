package com.util.http.strategy;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

public class WebClientStrategy extends BaseWorkStrategy {

    @Override
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params){
        WebClient webClient = WebClient.create();
        String reqUrl = extentUrl(url, params);
        WebClient.RequestHeadersSpec<?> reqSpec = webClient.get().uri(reqUrl);
        addHeaders(reqSpec, headers);
        Mono<String> mono = reqSpec.retrieve().bodyToMono(String.class);
        return mono.block();
    }

    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        WebClient webClient = WebClient.create();
        WebClient.RequestBodySpec reqSpec = webClient.post().uri(url);
        addHeaders(reqSpec, headers);
        reqSpec.body(BodyInserters.fromObject(params));
        Mono<String> mono = reqSpec.retrieve().bodyToMono(String.class);
        return mono.block();
    }

    private void addHeaders(WebClient.RequestHeadersSpec<?> reqSpec, Map<String, String> headers) {
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            reqSpec = reqSpec.header(headerName, headers.get(headerName));
        }
    }
}
