package com.example.rpc.client;

import com.example.rpc.config.FeignHeaderConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "mock-api",
            url = "http://localhost:8081/",
            configuration = {FeignHeaderConfiguration.class})
public interface FeignRpcInterface {

    /**
     * default
     * @return
     */
    @GetMapping(value = "/items")
    String getItems();

    /**
     * default
     * @param id
     * @return
     */
    @GetMapping(value = "/items/{id}")
    String getItem(@PathVariable("id") Long id);

    /**
     * add header1
     * @return
     */
    @GetMapping(value = "/items/headers", headers = "key1=value1")
    String getItemsHeaders1();

    /**
     * add header2
     * @param headers
     * @return
     */
    @GetMapping(value = "/items/headers")
    String getItemsHeaders2(@RequestHeader("key1") String headers);
}
