package com.zgf.springcloud.config.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by zgf on 17/4/20.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigRepoStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigRepoStartApplication.class, args);
    }
}
