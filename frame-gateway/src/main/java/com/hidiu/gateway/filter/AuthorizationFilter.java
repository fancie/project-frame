package com.hidiu.gateway.filter;

import com.hidiu.gateway.contants.Contants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @title: feign客户端权限过滤
 * @projectName frame-gateway
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    /**
     * 这个过滤器是验证客户端是有权限访问相应的feign接口，不需要的可以去掉
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        var authToken = request.getHeaders().get("authToken");

        if(Contants.isInOpenFeignServices(path)){
            var applicationNameHeader = request.getHeaders().get("ApplicationName");
            boolean tokenIsRight = authToken != null && authToken.size() > 0 && Contants.openFeignAuthToken.equals(authToken.get(0));
            String applicationName = applicationNameHeader != null && applicationNameHeader.size() > 0 ? applicationNameHeader.get(0) : "";
            boolean appHasRight = Contants.havaRight(applicationName, path);
            if(tokenIsRight && appHasRight){
                //放行
                return chain.filter(exchange);
            }
            //拒绝
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE); //这个状态码是406
            return exchange.getResponse().setComplete();
        }
        //其它是web服务，都放行，交给其它过滤器继续过滤
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
