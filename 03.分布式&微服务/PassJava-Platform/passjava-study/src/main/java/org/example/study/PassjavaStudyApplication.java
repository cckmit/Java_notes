package org.example.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午5:17
 */
@MapperScan("org.example.study.dao")
@SpringBootApplication
public class PassjavaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaStudyApplication.class, args);
    }
}
