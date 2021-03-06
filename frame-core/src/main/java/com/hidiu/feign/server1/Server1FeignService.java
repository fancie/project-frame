package com.hidiu.feign.server1;

import com.hidiu.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gateway", path = "/server1", configuration = FeignConfiguration.class)
public interface Server1FeignService {

    @PostMapping(value = "/user/save")
    String save();

}
