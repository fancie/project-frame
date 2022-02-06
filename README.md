# 项目文档
 开箱即用的spring-cloud集成方案，包括nacos、sentinal、openfeign、gateway、spring-boot-admin、security、seata、elasticsearch、ELK日志等组件 

1.项目启动条件(版本可以自行选择，只需跟依赖匹配)
------------
  1.1 Nacos
  
   版本号：NACOS1.4.3
   
   启动命令：sh startup.sh -m standalone
   
   配置项内容(需要根据自己的需要做相应修改，所有配置都不是必须的，可以写到项目配置文件里面，本人只是为了做测试，部分内容从nacos读取，部分内容写入项目配置文件)
   ```
   1.1.1 jpa-config.yml（JPA配置，Group：DEFAULT_GROUP，格式：yaml）
   spring:
    jpa:
      hibernate:
        ddl-auto: update
      generate-ddl: true
      show-sql: true
      properties:
        hibernate.jdbc.time_zone: Asia/Shanghai
   
   1.1.2 mysql-data-config.yml（mysql数据库配置，Group：DEFAULT_GROUP，格式：yaml）
   spring:
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/test
      username: root
      password:
      
   1.1.3 authToken（openfeign的tonken，Group：DEFAULT_GROUP，格式：text）
   EbDcwcLG3zfGdiHFFzSQbbf4Y1epwOK14wRGvqt8BmYbW0FpWKY

   
   1.1.4 gateway-degrade-rules（sentinal熔断规则，Group：mytest，格式：json）
   [
     {
        "resource": "/web/r",
        "limitApp": "default",
        "grade": 0,
        "count": 10,
        "timeWindow": 5,
        "clusterMode": false
    }
   ]
   
   1.1.5 gateway-flow-rules（sentinal限流规则，Group：mytest，格式：json）
   [
    {
        "resource": "/web",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
   ]
   ```
  1.2 Seata-server
  
  版本号：1.3.0
  ```
  启动命令：sh ./bin/seata-server.sh -p 8091 -m file
  ```
  
  1.3 EKL
  ```
  elasticsearch版本：7.17
  kibana版本：7.17
  logstash版本：7.17
  ```
 
  1.4 Sentinal-dashboard（非必需）
  ```
  版本号：在github下载的最新版
  
  编译命令：mvn clean package -DskipTests #编译时需要跳过test
  
  启动命令：java -Dserver.port=8008 -Dcsp.sentinel.dashboard.server=localhost:8008 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
  ```
2.项目介绍
------------
 2.1 子项目描述
 
 ```
  frame-admin: spring-boot-admin监控监控项目
  frame-gateway：网关，所有项目访问都需要经过网关
  frame-core: 引入的基础类、openfeign客户端调用、全局事物相关
  frame-server1: 模拟users表的访问，只能web访问
  frame-server2: 模拟orders表的访问，只能web访问
  frame-web：对外提供web服务，可以前后端分离，也可以不分
 ```
 2.2 项目启动
 ```
  项目启动无先后顺序
  启动所有项目：frame-admin、frame-gateway、frame-server1、frame-server2、frame-web
 ```
 2.3 项目调用关系
 ```
  frame-gateway----->frame-web---(frame-gateway)-->frame-server1/frame-server2
 ```
 2.4 测试效果
 ```
  2.4.1 访问：http://localhost:8899/web/r/
  返回：相应的frame-web项目的 /r/ 地址下的内容
  
  2.4.1 访问：http://localhost:8899/web/tx/
  返回：相应的frame-web项目的 /tx/ 地址下的内容
  这个地址可以测试seata的分布式事务，分别调用frame-server1和frame-server2服务向数据库新增一个用户和一个订单（数据纯测试，可以忽略）
 ```
3.其它项目资料
------------
 3.1 [Nacos配置和注册](https://github.com/fancie/project-frame/wiki/1.-Nacos配置&注册)
 
 3.2 [路由规则](https://github.com/fancie/project-frame/wiki/2.-路由规则)

 3.3 [限流和熔断](https://github.com/fancie/project-frame/wiki/3.-限流&熔断)

 3.4 [安全和拦截](https://github.com/fancie/project-frame/wiki/4.-安全&拦截)
 
 3.5 [分布式事务](https://github.com/fancie/project-frame/wiki/5.-分布式事务)
 
 3.6 [监控安全认证](https://github.com/fancie/project-frame/wiki/6.-监控安全认证)


--------------
其它有问题的可以联系我：1084961@qq.com

2022-02-07 杭州
