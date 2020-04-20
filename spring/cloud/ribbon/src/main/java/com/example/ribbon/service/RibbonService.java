package com.example.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RibbonService {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    public String getItems() {
        /**
         * loadBalancerClient를 이용하여 직접 host, port를 가져와서 세팅할 수 있다.
         */
        final ServiceInstance choose = loadBalancerClient.choose("ribbon-service");
        System.out.println("host :: " + choose.getHost());
        System.out.println("port :: " + choose.getPort());

        /**
         * application.yaml파일에 직접 접근하여 host, port 값을 설정
         */
        String url = UriComponentsBuilder.fromHttpUrl("http://ribbon-service/items")
//                .queryParam("", "")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
