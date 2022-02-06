package com.hidiu.es.service.impl;

import com.hidiu.es.entity.News;
import com.hidiu.es.repository.NewsRepository;
import com.hidiu.es.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fancie
 * @title: NewsServiceImpl
 * @projectName project-es
 * @description: TODO
 * @date 2022/2/6 下午8:53
 */
@Component
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findById(String id) {
        return newsRepository.findById(id).get();
    }
}
