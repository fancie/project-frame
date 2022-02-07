package com.hidiu.server1.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

/**
 * @author fancie
 * @title: JpaInterceptor
 * @projectName project-server1
 * @description: TODO
 * @date 2022/2/7 上午3:29
 */
@Slf4j
public class JpaInterceptor implements StatementInspector{

    @Override
    public String inspect(String sql) {
        log.info("sql:--" + sql);
        return sql;
    }
}
