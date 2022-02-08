package com.hidiu.web.controller;

import com.hidiu.feign.es.EsFeignService;
import com.hidiu.service.TransactionService;
import com.hidiu.utils.StringUtils;
import com.hidiu.vo.NewsVo;
import com.hidiu.web.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @title: 测试控制类
 * @projectName frame-web
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EsFeignService esFeignService;

    @Autowired
    private RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping("/tx")
    public String tx(){
        log.info("#--正在访问web项目的/tx方法--#");
        transactionService.testTranaction();
        String random = StringUtils.randomStr(64);
        return  "tx---------" + random;
    }

    @ResponseBody
    @RequestMapping("/r")
    public String r(){
        log.info("#--正在访问web项目的/r方法--#");
        String random = StringUtils.randomStr(64);
        return  "r----------" + random;
    }

    @ResponseBody
    @RequestMapping("/news/{id}")
    public String getNews(@PathVariable("id") String id){
        log.info("#--正在访问web项目的/news/id方法--#");
        NewsVo newsVo = esFeignService.findById(id);
        return  "get----------" + newsVo.getTitle();
    }

    @ResponseBody
    @RequestMapping("/redis/{key}/{value}")
    public String redis(@PathVariable("key") String key, @PathVariable("value") String value){
        log.info("#--正在访问web项目的/redis测试方法--#");
        redisUtil.set(key, value, 360);
        String valueNew = "";
        if(redisUtil.hasKey(key)){
            valueNew = (String) redisUtil.get(key);
        }
        return  "redis----------key：" + key + "----------value：" + valueNew;
    }

}
