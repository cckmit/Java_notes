package org.example.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cat
 * @description
 * @date 2022/5/3 下午3:14
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PassjavaSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassjavaSearchApplication.class, args);
    }
}

