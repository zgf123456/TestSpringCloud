package com.zgf.springclound.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by zgf on 17/4/12.
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableZuulProxy
public class GatewayStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayStartApplication.class, args);
    }
}
