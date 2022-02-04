package com.hidiu.gateway.route;

import com.hidiu.gateway.filter.RequestGatewayFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GatewayRoutesConfiguration {

    /**
     * - id: server2
     *   uri: lb://server2
     *   predicates:
     *     - Path=/server2/**
     *   ilters:
     *     - StripPrefix=1
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        log.info("GatewayRoutesConfiguration filter........");
        return builder.routes()
                .route("server2", r ->r.path("/server2/**")
                        .filters(f -> f.stripPrefix(1)
                        .filters(new RequestGatewayFilter()))
                        .uri("lb://server2")
                ).build();
    }
}
