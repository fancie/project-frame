package com.hidiu.feign.es.impl;

import com.hidiu.feign.es.EsFeignService;
import com.hidiu.vo.NewsVo;
import org.springframework.stereotype.Component;

@Component
public class EsFeignServiceImpl implements EsFeignService {

    @Override
    public String save() {
        return null;
    }

    @Override
    public NewsVo findById(String id){
        return null;
    }
}
