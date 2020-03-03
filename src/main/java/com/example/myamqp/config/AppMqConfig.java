package com.example.myamqp.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.mq")
@EnableRabbit
public class AppMqConfig {
    private final Logger log = LoggerFactory.getLogger(AppMqConfig.class);
    private Map<String, QueueConfig> queues = new HashMap<String, QueueConfig>();

    public AppMqConfig() {
        log.info("[RabbitMQ] RabbitMQ Habilitado");
    }
    public Map<String, QueueConfig> getQueues() {
        return queues;
    }
    public static class QueueConfig {
        private String name;
        private Boolean autoCreate = false;
        public String getName() {
            return name;
        }
        public Boolean getAutoCreate() {
            return autoCreate;
        }
        public void setAutoCreate(Boolean autoCreate) {
            this.autoCreate = autoCreate;
        }
        public void setName(String name) {
            this.name = name;
        }       
    }
}