package com.hidiu.es.repository;

import com.hidiu.es.entity.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fancie
 * @title: NewsRepository
 * @projectName project-es
 * @description: TODO
 * @date 2022/2/6 下午8:49
 */
@Repository
public interface NewsRepository extends ElasticsearchRepository<News, String> {

}
