package com.zgf.springclound.consumer;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zgf.springclound.consumer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zgf on 17/4/9.
 */
@RestController
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon/{id}")
    @HystrixCommand(fallbackMethod = "fallback")
    public User findById(@PathVariable String id) {
        User user = restTemplate.getForObject("http://springclound-eureka-provider/" + id, User.class);
        System.out.println("findById " + JSON.toJSONString(user));
        return user;
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
