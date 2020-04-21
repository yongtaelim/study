package com.example.webflux;

import com.example.webflux.model.DemoModel;
import com.example.webflux.response.DemoResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebfluxApplicationTests {

    @Autowired(required = false)
    private WebTestClient webTestClient;

    @Autowired
    private ModelMapper modelMapper;

    private RestTemplate restTemplate;
    private StopWatch stopWatch;
    private WebClient webClient;

    private String THREE_SECONDS_URL;
    private int LOOP_COUNT;

    @BeforeAll
    static void initAll() {
        System.out.println("before all...");
    }

    @BeforeEach
    void init() {
        this.restTemplate = new RestTemplate();
        this.webClient = WebClient.create();
        this.stopWatch = new StopWatch();
        this.THREE_SECONDS_URL = "http://localhost:8081";
        this.LOOP_COUNT = 3;
    }

    @Test
    @DisplayName("test webflux")
    void contextLoads() {
        DemoResponse response = new DemoResponse();
        DemoModel model = new DemoModel();
        model.setId("1");
        response.setContent(model);
        response.setResult(true);

        ArrayList responseBody = this.webTestClient.post().uri("/demo").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(model), DemoModel.class).exchange()
                .expectStatus().isOk()
                .expectBody(ArrayList.class)
                .returnResult().getResponseBody();

        DemoResponse demoResponse = modelMapper.map(responseBody.get(0), DemoResponse.class);

        assertTrue(demoResponse.isResult());
        assertEquals(demoResponse.getContent().getId(), "1");
    }

    @Test
    @DisplayName("test blocking I/O")
    public void blockingTest() {
        stopWatch.start();

        for (int i = 0; i < LOOP_COUNT; i++) {
            final ResponseEntity<String> response = restTemplate.exchange(THREE_SECONDS_URL + "/three/block", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        }
        stopWatch.stop();

        System.out.println("blocking Test total times :: " + stopWatch.getTotalTimeSeconds());
        assertTrue(stopWatch.getTotalTimeSeconds() > 9);
    }

    @Test
    @DisplayName("test non-blocking I/O")
    public void nonBlocking() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(LOOP_COUNT);
        stopWatch.start();

        for (int i = 0; i < LOOP_COUNT; i++) {
            this.webClient
                    .get()
                    .uri(THREE_SECONDS_URL + "/three/block")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(it -> {
                count.countDown();
                System.out.println(it);
            });
        }
        count.await(10, TimeUnit.SECONDS);
        stopWatch.stop();
        System.out.println("non-blocking test total times :: " + stopWatch.getTotalTimeSeconds());
    }
}
