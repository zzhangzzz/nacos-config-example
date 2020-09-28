package com.dubbo.spring.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DubboCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCloudApplication.class, args);
    }

}
