package com.example.rpc.controller;

import com.example.rpc.service.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpcController {

    private RpcService rpcService;

    @Autowired
    public RpcController(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    @GetMapping(value = "/items")
    public String getItems() {
        return rpcService.getItems();
    }
    @GetMapping(value = "/items/{id}")
    public String getItem(@PathVariable("id") Long id) {
        return rpcService.getItem(id);
    }

    @GetMapping(value = "/items/header1")
    public String getItemsHeaders1() {
        return rpcService.getItemsHeaders1();
    }

    @GetMapping(value = "/items/header2")
    public String getItemsHeaders2() {
        return rpcService.getItemsHeaders2();
    }
}
