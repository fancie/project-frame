package com.hidiu.feign.es;

import com.hidiu.config.FeignConfiguration;
import com.hidiu.vo.NewsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gateway", path = "/es", configuration = FeignConfiguration.class)
public interface EsFeignService {

    @PostMapping(value = "/news/save")
    String save();

    @PostMapping(value = "/news/get")
    NewsVo findById(@RequestParam("id") String id);
}
