package com.hidiu.server2.controller;

import com.hidiu.server2.entity.Orders;
import com.hidiu.server2.service.OrdersService;
import com.hidiu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/order/save")
    @ResponseBody
    public String save(){
        Orders order = new Orders();
        order.setUserId(3);
        ordersService.save(order);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/")
    public String random(){
        String random = StringUtils.randomStr(64);
        return  "random------" + random;
    }
}
