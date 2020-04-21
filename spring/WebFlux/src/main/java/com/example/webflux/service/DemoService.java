package com.example.webflux.service;

import com.example.webflux.model.DemoModel;
import com.example.webflux.response.DemoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class DemoService {

    public Flux<DemoResponse> post(Flux<DemoModel> demoModelFlux) {
        return demoModelFlux.flatMap(demoModel -> {
            System.out.println("demoModel :: " + demoModel);
            return Flux.just(new DemoResponse(demoModel, true));
        });
    }
}
