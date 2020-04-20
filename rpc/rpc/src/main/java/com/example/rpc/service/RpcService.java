package com.example.rpc.service;

import com.example.rpc.client.FeignRpcInterface;
import com.example.rpc.client.feign.FeignRpcClient;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;

@Service
public class RpcService {

    @Autowired
    private FeignRpcInterface feignClient;

    public String getItems() {
        return feignClient.getItems();
    }

    public String getItem(Long id) {
        return feignClient.getItem(id);
    }

    public String getItemsHeaders1() {
        return feignClient.getItemsHeaders1();
    }

    public String getItemsHeaders2() {
        String headerValue = "value1";
        return feignClient.getItemsHeaders2(headerValue);
    }
}
