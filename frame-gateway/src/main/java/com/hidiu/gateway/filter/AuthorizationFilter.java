package com.hidiu.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //禁止访问
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE); //这个状态码是406
//        return exchange.getResponse().setComplete();
        //放行
        return chain.filter(exchange);
    }

    /**
     * 这是Ordered接口的中的方法
     * 过滤器有一个优先级的问题，这个值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
