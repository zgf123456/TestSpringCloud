package com.zgf.springcloud.stream.sync.source;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zgf on 17/4/15.
 */
@EnableBinding
@Controller
public class TestSyncSteam {
    private Logger logger = LoggerFactory.getLogger(TestSyncSteam.class);

    @Autowired
    private BinderAwareChannelResolver resolver;

//    @Autowired
//    @Qualifier("sourceChannel")
//    private MessageChannel localChannel;

//@Bean(name = "sourceChannel")
//public MessageChannel localChannel() {
//    return new QueueChannel();
//}

    @RequestMapping(path = "/{id}")
    @ResponseBody
    public String handleRequest(@PathVariable String id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        sendMessage(map, "application/json");
        return "SUCCESS";
    }

    private void sendMessage(Object body, Object contentType) {
        QueueChannel queueChannel = new QueueChannel();
        queueChannel.send(MessageBuilder.createMessage(body,
                new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, contentType))));
        Message<?> receive = queueChannel.receive(60000);
        System.out.println("received: " + JSON.toJSONString(receive.getPayload()));
    }

//    @ServiceActivator(inputChannel = "sourceChannel")
//    public void loggerSink(Object payload) {
//        logger.info("Received: " + payload);
//    }

    @Bean
    @ServiceActivator(inputChannel = "sourceChannel")
    public ExpressionEvaluatingRouter router() {
        ExpressionEvaluatingRouter router = new ExpressionEvaluatingRouter(new SpelExpressionParser().parseExpression("payload.id"));
        router.setDefaultOutputChannelName("zgf-test-sync");
        router.setChannelResolver(resolver);
        return router;
    }
}


