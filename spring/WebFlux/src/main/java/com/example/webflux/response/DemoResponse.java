package com.example.webflux.response;

import com.example.webflux.model.DemoModel;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DemoResponse {

    private DemoModel content;
    private boolean result;
}
