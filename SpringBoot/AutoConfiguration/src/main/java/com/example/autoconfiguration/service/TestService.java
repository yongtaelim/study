package com.example.autoconfiguration.service;

import com.example.autoconfiguration.config.ThreadPoolConfiguration;
import com.example.autoconfiguration.task.printingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
public class TestService {

    @Autowired
    private ThreadPoolConfiguration threadPool;

    public void test() {
        Executor executor = threadPool.threadPoolTaskExecutor();
        executor.execute(new printingTask());
    }
}
