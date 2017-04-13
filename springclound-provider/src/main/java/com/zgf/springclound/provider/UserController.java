package com.zgf.springclound.provider;

import com.alibaba.fastjson.JSON;
import com.zgf.springclound.provider.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zgf on 17/4/9.
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger userIndex = new AtomicInteger(0);

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") String id) {
        User user = new User();
        user.setId(id);
        user.setAge(20);
        user.setName("JACK" + userIndex.getAndIncrement());
        try {
            logger.info("thread sleep start " + JSON.toJSONString(user));
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("findById " + JSON.toJSONString(user));
        return user;
    }

    @GetMapping("/instance-info")
    public ServiceInstance showInfo() {
        ServiceInstance localServiceInstance = discoveryClient.getLocalServiceInstance();
        return localServiceInstance;
    }
}
