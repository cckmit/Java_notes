package org.example.channel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午5:16
 */
@MapperScan("org.example.channel.dao")
@SpringBootApplication
public class PassjavaChannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaChannelApplication.class, args);
    }
}
