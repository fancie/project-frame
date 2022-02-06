package com.hidiu.es.service;

import com.hidiu.es.entity.News;

/**
 * @author fancie
 * @title: NewsService
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/6 下午8:49
 */
public interface NewsService {

    News save(News news);

    News findById(String id);

}
