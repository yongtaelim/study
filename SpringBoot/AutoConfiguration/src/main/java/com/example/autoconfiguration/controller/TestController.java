package com.example.autoconfiguration.controller;

import com.example.autoconfiguration.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ResponseBody
    @GetMapping(value = "/get")
    public String test() {
        testService.test();
        return "ok";
    }
}
