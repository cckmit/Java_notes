package org.example.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午5:17
 */
@EnableDiscoveryClient
@MapperScan("org.example.content.dao")
@SpringBootApplication
public class PassjavaContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaContentApplication.class, args);
    }
}
