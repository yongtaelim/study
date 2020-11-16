package com.example.querydsl.configuration;

import com.p6spy.engine.logging.P6LogOptions;
import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class P6spyLogMessageFormatConfiguration {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfiguration.class.getName());      // pretty

        // exclude sql pattern
        P6LogOptions.getActiveInstance().setFilter(true);
        P6LogOptions.getActiveInstance().setExclude("store");
    }
}
