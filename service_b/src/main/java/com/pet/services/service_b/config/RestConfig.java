package com.pet.services.service_b.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class RestConfig {
    @Value("${service-c.uri}")
    private String cServiceUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(cServiceUrl)
                .requestInterceptor((request, body, execution) -> {
                    String traceId = MDC.get("traceId");
                    if (traceId != null) {
                        request.getHeaders().add("X-Request-Id", traceId);
                    }

                    return execution.execute(request, body);
                })
                .build();
    }
}
