package com.example.demoserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    private Map<Long, Object> map = new HashMap<Long, Object>();

    public DemoController() {
        map.put(1L, "item1");
        map.put(2L, "item2");
        map.put(3L, "item3");
    }

    @GetMapping(value = "/items")
    public Map<Long, Object> getItems() {
        System.out.println("Method :: getItems");
        return map;
    }

    @GetMapping(value = "/items/headers")
    public Map<Long, Object> getItemsHeaders(@RequestHeader("key1") String header) {
        System.out.println("header :: " + header);
        return map;
    }

    @GetMapping(value = "/items/{id}")
    public Object getItem(@PathVariable("id") Long id) {
        return map.get(id);
    }

    @GetMapping(value = "/ping")
    public String getAliveCheck() {
        return "alive";
    }

}
