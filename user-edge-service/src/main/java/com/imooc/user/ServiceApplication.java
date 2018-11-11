package com.imooc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 用户服务-页面请求处理服务
 * @EnableCaching  redis2.0以后使用该注释以及yml中相应配置文件，即可使用redis
 */
@SpringBootApplication
@EnableCaching
public class ServiceApplication {
    public static void main(String args[]){
        SpringApplication.run(ServiceApplication.class,args);
    }
}
