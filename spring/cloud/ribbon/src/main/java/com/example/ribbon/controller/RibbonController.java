package com.example.ribbon.controller;

import com.example.ribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @GetMapping(value = "/items")
    public String getItems() {
        return ribbonService.getItems();
    }
}
