package com.hidiu.web.controller;

import com.hidiu.service.TransactionService;
import com.hidiu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private TransactionService transactionService;

    @ResponseBody
    @RequestMapping("/tx")
    public String tx(){
        transactionService.testTranaction();
        String random = StringUtils.randomStr(64);
        return  "tx---------" + random;
    }

    @ResponseBody
    @RequestMapping("/r")
    public String r(){
        String random = StringUtils.randomStr(64);
        return  "r----------" + random;
    }

}
