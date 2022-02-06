package com.hidiu.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author fancie
 * @title: FeignConfiguration
 * @projectName project-core
 * @description: TODO
 * @date 2022/2/5 下午10:26
 */
@Slf4j
@Configuration
public class FeignConfiguration implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String applicationName;

    public FeignConfiguration() {
    }

    @Override
    public void apply(RequestTemplate template) {
        // 携带token到Feign的服务端
        template.getRequestVariables();
        template.header("Authorization", "chainTokenValue");
        //在网关层用来控制资源访问路径，比如：web可以访问server1和server2，但是server2不能访问server1（这个可以在网关层调整）
        template.header("ApplicationName", applicationName);
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5000, TimeUnit.MINUTES, 10000, TimeUnit.MINUTES, true);
    }

}
