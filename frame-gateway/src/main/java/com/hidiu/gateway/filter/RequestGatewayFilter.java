package com.hidiu.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @title: 请求拦截，例如拦截ip，useragent等
 * @projectName frame-gateway
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
@Slf4j
public class RequestGatewayFilter implements GatewayFilter, Ordered {

    /**
     * 这里可以通过后台配置规则，然后推送到所有nacos注册的gateway服务，所有gateway服务根据最新规则处理
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("ServerGatewayFilter filter ");
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String userAgent = headers.get("User-Agent").toString();
        if(userAgent.indexOf("spider") < 0){
            return chain.filter(exchange); // 放行
        }
        return null;
    }

    @Override
    public int getOrder() {
        //拦截器优先级，越小位最先执行
        return 1;
    }
}
