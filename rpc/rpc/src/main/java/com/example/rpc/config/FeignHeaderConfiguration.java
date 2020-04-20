package com.example.rpc.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignHeaderConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("header","header1","header2");
    }
}
