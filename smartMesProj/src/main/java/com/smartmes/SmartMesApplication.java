package com.smartmes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Smart MES 系统启动类
 * 这是整个应用的入口点
 */
@SpringBootApplication
@MapperScan("com.smartmes.mapper")  // 扫描MyBatis的Mapper接口
public class SmartMesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartMesApplication.class, args);
        System.out.println("Smart MES 系统已启动！");
    }

}