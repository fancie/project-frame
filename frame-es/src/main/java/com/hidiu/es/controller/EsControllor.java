package com.hidiu.es.controller;

import com.hidiu.es.entity.News;
import com.hidiu.es.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author fancie
 * @title: EsControllor
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/6 下午8:45
 */
@Slf4j
@Controller
public class EsControllor {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/news/save")
    @ResponseBody
    public String get(){
        News news = new News();
        news.setId(UUID.randomUUID().toString());
        news.setTitle("第一条新闻");
        news.setContent("这是第一条新闻，这是第一条新闻，这是第一条新闻");
        newsService.save(news);
        log.info("ID = " + news.getId());
        return "OK";
    }

    @RequestMapping("/news/get/{id}")
    @ResponseBody
    public String get(@PathVariable("id") String id){
        News news = newsService.findById(id);
        log.info("Title = " + news.getTitle());
        return "OK";
    }
}
