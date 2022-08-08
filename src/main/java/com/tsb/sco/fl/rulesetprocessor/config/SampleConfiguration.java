package com.tsb.sco.fl.rulesetprocessor.config;

import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SampleConfiguration {

    RestTemplate restTemplate = null;
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        httpComponentsClientHttpRequestFactory.setConnectTimeout(3000);
        httpComponentsClientHttpRequestFactory.setReadTimeout(59000);
        restTemplate = builder
                .requestFactory(() -> httpComponentsClientHttpRequestFactory)
                .build();
        return restTemplate;
    }
}
