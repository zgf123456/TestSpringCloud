package com.zgf.springclound.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Created by zgf on 17/4/12.
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableHystrixDashboard
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
