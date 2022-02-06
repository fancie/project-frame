package com.hidiu.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * @author fancie
 * @title: NewsVo
 * @projectName project-core
 * @description: TODO
 * @date 2022/2/6 下午8:47
 */
@Data
public class NewsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    //标题
    private String title;

    //内容
    private String content;


}
