package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//开启redis注解版缓存
@EnableCaching
//mapper接口扫描器，指定mapper接口所在包，然后会自动mapper接口创建代理对象并加入到IOC容器
//@MapperScan(basePackages = "com.pn.mapper")
public class WarehouseMangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseMangerApplication.class, args);
    }

}
