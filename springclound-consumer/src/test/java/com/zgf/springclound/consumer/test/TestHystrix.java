package com.zgf.springclound.consumer.test;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

/**
 * Created by zgf on 17/4/13.
 */
public class TestHystrix {
    private static Logger logger = LoggerFactory.getLogger(TestHystrix.class);

    public static void main(String[] args) throws Exception {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet httpGet = new HttpGet("http://localhost:8010/ribbon/aaa");
                    HttpClient httpClient = HttpClients.createDefault();
                    logger.info(Thread.currentThread().getId() + ">>>>>>>>>>>start");
                    HttpResponse resp = httpClient.execute(httpGet);
                    StringWriter stringWriter = new StringWriter(100);
                    IOUtils.copy(resp.getEntity().getContent(), stringWriter, "UTF-8");
                    logger.info(Thread.currentThread().getId() + ">>>>>>>>>>>" + stringWriter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 20; i++) {
            new Thread(runable).start();
        }
    }
}
