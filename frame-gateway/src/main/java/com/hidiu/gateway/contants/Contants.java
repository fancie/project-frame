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

    //案例表示web可以访问任何资源，server1可以访问server2，但是server2访问server1不在列
    public static List<String> feignAppRight = Arrays.asList("web-*", "server1-server2");

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

    public static boolean havaRight(String applicationName, String path){
        String[] strs = path.split("/");
        if(strs.length > 0){
            String r1 = applicationName + "-*";
            String r2 = applicationName + "-" + strs[0];
            for(String right : feignAppRight){
                if(right.equals(r1) || right.equals(r2)){
                    return true;
                }
            }
        }
        return false;
    }

}
