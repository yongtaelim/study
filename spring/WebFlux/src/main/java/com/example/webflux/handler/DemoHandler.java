package com.example.webflux.handler;

import com.example.webflux.model.DemoModel;
import com.example.webflux.response.DemoResponse;
import com.example.webflux.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DemoHandler {

    private final Validator validator;
    private final DemoService demoService;

    public Mono<ServerResponse> post(ServerRequest serverRequest) {
        Flux<DemoResponse> ret = demoService.post(
            serverRequest.bodyToFlux(DemoModel.class).filter(demoModel -> {
                Set<ConstraintViolation<DemoModel>> validationResult = validator.validate(demoModel);

                if(validationResult.isEmpty()) {
                    System.out.println("validation result :: true");
                    System.out.println("validation retuls :: " + validationResult);
                    System.out.println("demo model :: " + demoModel.toString());
                    return true;
                } else {
                    System.out.println("validation result :: false");
                    return false;
                }
            })
        );
        return ServerResponse.ok().body(ret, DemoResponse.class);
    }
}
