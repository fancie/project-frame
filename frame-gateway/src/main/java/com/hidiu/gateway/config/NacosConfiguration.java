package com.hidiu.gateway.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@Slf4j
@Configuration
public class NacosConfiguration {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String SERVER_ADDR;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    public void getConfigFromNacos() throws NacosException {
        Properties properties = new Properties();
        // nacos服务器地址，127.0.0.1:8848
        properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDR);
        // 配置中心的命名空间id
        properties.put(PropertyKeyConst.NAMESPACE, "");
        properties.put(PropertyKeyConst.USERNAME, "nacos");
        properties.put(PropertyKeyConst.PASSWORD, "nacos");
        ConfigService configService = NacosFactory.createConfigService(properties);
        // 根据dataId、group定位到具体配置文件，获取其内容. 方法中的三个参数分别是: dataId, group, 超时时间
        String content = configService.getConfig("gateway-flow-rules", "mytest", 3000L);
        log.info(content);
    }

    @Bean
    public void getServiceFromNacos() {
        List<ServiceInstance> instances = discoveryClient.getInstances("Testweb");
        log.info("Gateway的服务数量：" + instances.size());
    }

}
