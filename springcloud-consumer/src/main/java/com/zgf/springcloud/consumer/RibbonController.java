package com.zgf.springcloud.consumer;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.zgf.springcloud.consumer.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Created by zgf on 17/4/9.
 */
@RestController
//@DefaultProperties(commandProperties = {
//        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//}, threadPoolProperties = {
//        @HystrixProperty(name = "coreSize", value = "1"),
//        @HystrixProperty(name = "maxQueueSize", value = "1"),
//        @HystrixProperty(name = "keepAliveTimeMinutes", value = "1"),
//        @HystrixProperty(name = "queueSizeRejectionThreshold", value = "1"),
//        @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
//        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
//})
public class RibbonController {
    private Logger logger = LoggerFactory.getLogger(RibbonController.class);
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon/{id}")
//    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    }, threadPoolProperties = {
//            @HystrixProperty(name = "coreSize", value = "1"),
//            @HystrixProperty(name = "maxQueueSize", value = "1"),
//            @HystrixProperty(name = "keepAliveTimeMinutes", value = "1"),
//            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "1"),
//            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
//            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
//    })
    @HystrixCommand(fallbackMethod = "fallback")
    public User findById(@PathVariable String id) {
        logger.info("findById start >>>>>");
        User user = restTemplate.getForObject("http://springclound-eureka-provider/" + id, User.class);
        logger.info("findById " + JSON.toJSONString(user));
        return user;
    }

    @GetMapping("/ribbon/async/{id}")
    @HystrixCommand(fallbackMethod = "fallback")
    public Future<User> findByIdAsyn(@PathVariable final String id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                User user = restTemplate.getForObject("http://springclound-eureka-provider/" + id, User.class);
                System.out.println("findById " + JSON.toJSONString(user));
                return user;
            }
        };
    }

    @GetMapping("/instance-info")
    public ServiceInstance showInfo() {
        ServiceInstance localServiceInstance = discoveryClient.getLocalServiceInstance();
        return localServiceInstance;
    }

    public User fallback(String id) {
        User user = new User();
        user.setId(id);
        user.setAge(20);
        user.setName("fallback");
        System.out.println("findById fallback " + JSON.toJSONString(user));
        return user;
    }
}
