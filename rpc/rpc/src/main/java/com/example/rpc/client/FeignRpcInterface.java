package com.example.rpc.client;

import com.example.rpc.config.FeignHeaderConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feign-api",
        url = "http://localhost:8081/",
        configuration = {FeignHeaderConfiguration.class})
public interface FeignRpcInterface {

    /**
     * url : http://localhost:8080/items
     * type : GET
     * @return
     */
    @GetMapping(value = "/items")
    String getItems();

    /**
     * url : http://localhost:8080/items/{id}
     * type : GET
     * @param id
     * @return
     */
    @GetMapping(value = "/items/{id}")
    String getItem(@PathVariable("id") Long id);

    /**
     * url : http://localhost:8080/items
     * type : GET
     * header : {"key1" : "value1"}
     * @return
     */
    @GetMapping(value = "/items/headers", headers = "key1=value1")
    String getItemsHeaders1();

    /**
     * url : http://localhost:8080/items
     * type : GET
     * header : {"key1" : "value1"}
     * @param headers
     * @return
     */
    @GetMapping(value = "/items/headers")
    String getItemsHeaders2(@RequestHeader("key1") String headers);
}