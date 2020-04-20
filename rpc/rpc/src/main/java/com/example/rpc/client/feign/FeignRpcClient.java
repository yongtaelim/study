package com.example.rpc.client.feign;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClient;

public class FeignRpcClient {

    private FeignClient userClient;

    private FeignClient adminClient;

    public FeignRpcClient(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("userNm","userPw"))
                .target(FeignClient.class, "demo6543523.mockable.io");

        Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("adminNm","adminPw"))
                .target(FeignClient.class, "demo6543523.mockable.io");
    }

    public FeignClient getUserClient() {
        return userClient;
    }

    public FeignClient getAdminClient() {
        return adminClient;
    }
}
