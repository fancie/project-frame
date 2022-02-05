package com.hidiu.service.impl;

import com.hidiu.feign.server1.Server1FeignService;
import com.hidiu.feign.server2.Server2FeignService;
import com.hidiu.service.TransactionService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @title: seata事务
 * @projectName frame-core
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Server1FeignService server1FeignService;
    @Autowired
    private Server2FeignService server2FeignService;

    @Override
    @GlobalTransactional
    public void testTranaction() {
        server1FeignService.save();
        server2FeignService.save();
    }
}
