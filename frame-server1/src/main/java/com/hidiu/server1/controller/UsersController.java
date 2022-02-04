package com.hidiu.server1.controller;

import com.hidiu.server1.entity.Users;
import com.hidiu.server1.service.UsersService;
import com.hidiu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("/user/save")
    @ResponseBody
    public String save(){
        Users user = new Users();
        user.setPassword("123");
        user.setUsername("admin");
        usersService.save(user);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/")
    public String index(){
        String random = StringUtils.randomStr(51);
        return  "index1" + random;
    }

}
