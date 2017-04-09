package com.zgf.springclound.consumer;

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
    public User findById(@PathVariable String id) {
        User user = restTemplate.getForObject("http://springclound-eureka-provider/" + id, User.class);
        return user;
    }

}
