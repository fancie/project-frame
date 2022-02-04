package com.hidiu.web.controller;

import com.hidiu.feign.server1.Server1FeignService;
import com.hidiu.feign.server2.Server2FeignService;
import com.hidiu.service.TransactionService;
import com.hidiu.utils.StringUtils;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class IndexController {

//    @Autowired
//    private Server1FeignService server1FeignService;
//    @Autowired
//    private Server2FeignService server2FeignService;
    @Autowired
    private TransactionService transactionService;

    @ResponseBody
    @RequestMapping("/")
    public String index(){
        transactionService.testTranaction();
        String random = StringUtils.randomStr(51);
        return  "index1" + random;
    }

    @ResponseBody
    @RequestMapping("/r")
    public String r(){
        String random = StringUtils.randomStr(51);
        return  "index2----------" + random;
    }

}
