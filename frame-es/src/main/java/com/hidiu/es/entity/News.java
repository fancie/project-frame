package com.hidiu.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author fancie
 * @title: News
 * @projectName project-es
 * @description: TODO
 * @date 2022/2/6 下午8:47
 */
@Data
@Document(indexName = "news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    //标题
    @Field(type = FieldType.Text)
    private String title;

    //内容
    @Field(type = FieldType.Text)
    private String content;


}
