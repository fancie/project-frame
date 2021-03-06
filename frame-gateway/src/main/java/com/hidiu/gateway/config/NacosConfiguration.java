package com.hidiu.gateway.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.hidiu.gateway.contants.Contants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @title: 测试nacos配置读取
 * @projectName frame-gateway
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
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
        String content = configService.getConfig("authToken", "DEFAULT_GROUP", 3000L);
        Contants.openFeignAuthToken = content;
        log.info(content);
    }

    @Bean
    public void getServiceFromNacos() {
        List<ServiceInstance> instances = discoveryClient.getInstances("web");
        log.info("Gateway的服务数量：" + instances.size());
    }

}
