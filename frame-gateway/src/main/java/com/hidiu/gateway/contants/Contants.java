package com.hidiu.gateway.contants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @title: 常量
 * @projectName frame-gateway
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
public class Contants {

    public static String openFeignAuthToken = "";

    public static List<String> openFeignServices = Arrays.asList("/server1/", "/server2/");

    /**
     * 验证是不是openFeign调用
     * @param path
     * @return
     */
    public static boolean isInOpenFeignServices(String path){
        for(String service : openFeignServices){
            if(path.startsWith(service)){
                return true;
            }
        }
        return false;
    }

}
