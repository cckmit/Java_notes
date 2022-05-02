package org.example.question;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableCaching
//@EnableFeignClients(basePackages = "com.jackson0714.passjava.question.feign")
//@EnableDiscoveryClient
@MapperScan("org.example.question.dao")
@SpringBootApplication
public class PassjavaQuestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassjavaQuestionApplication.class, args);
    }
}
