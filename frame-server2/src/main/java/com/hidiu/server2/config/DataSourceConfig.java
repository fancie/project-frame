package com.hidiu.server2.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.hidiu.utils.DataSourceNames;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fancie
 * @title: DataSourceConfig
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/7 上午11:43
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DruidDataSource masternDruidProperties() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return new DruidProperties().dataSource(dataSource);
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DruidDataSource slaveDruidProperties() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return new DruidProperties().dataSource(dataSource);
    }

    @Bean
    @Primary
    public RoutingDataSource dynamicDataSource(@Qualifier(value = "masterDataSource") DataSource masterDataSource, @Qualifier(value = "slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceNames.master, masterDataSource);
        targetDataSources.put(DataSourceNames.slave, slaveDataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource(masterDataSource, targetDataSources);
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }
}
