package com.hidiu.feign.server2;

import com.hidiu.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gateway", path = "/server2/", configuration = FeignConfiguration.class)
public interface Server2FeignService {

    @PostMapping(value = "/order/save")
    String save();

}
