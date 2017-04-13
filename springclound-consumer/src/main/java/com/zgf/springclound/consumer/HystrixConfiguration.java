package com.zgf.springclound.consumer;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zgf on 17/4/13.
 */
@Configuration
public class HystrixConfiguration {
    private Logger logger = LoggerFactory.getLogger(HystrixConfiguration.class);

    @Bean
    public HystrixCommandAspect hystrixAspect() {
        AbstractConfiguration configInstance = ConfigurationManager.getConfigInstance();
        configInstance.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "5000");

        configInstance.setProperty("hystrix.threadpool.default.coreSize", 10);
//        configInstance.setProperty("hystrix.threadpool.default.maximumSize", 3);  // 1.5.7版本才有的属性
        configInstance.setProperty("hystrix.threadpool.default.maxQueueSize", 100);
        configInstance.setProperty("hystrix.threadpool.default.queueSizeRejectionThreshold", 10);
        HystrixCommandAspect hystrixCommandAspect = new HystrixCommandAspect();
        return hystrixCommandAspect;
    }
}
