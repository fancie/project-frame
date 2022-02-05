# 项目文档
 开箱即用的spring-cloud集成方案，包括nacos、sentinal、openfeign、gateway、spring-boot-admin、security、seata等组件 

1.项目启动条件
------------
  1.1 Nacos
  
   版本号：NACOS1.4.3
   
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

其它文档还有待补充

