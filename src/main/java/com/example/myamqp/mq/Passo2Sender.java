package com.example.myamqp.mq;

import com.example.myamqp.config.AppMqConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class Passo2Sender {
    
    private final Logger log = LoggerFactory.getLogger(Passo2Sender.class);
    static private String QUEUE_KEY = "passo2";

    private RabbitTemplate rabbitTemplate;
    private String routingKey;

    public Passo2Sender(RabbitTemplate rabbitTemplate, AppMqConfig appMqConfig) {
        this.rabbitTemplate = rabbitTemplate;
        if (QUEUE_KEY != null) {
            this.routingKey = appMqConfig.getQueues().get(QUEUE_KEY).getName();
        }
    }

    public void send(DadoDto obj) {        
        log.info("[MQ SENDER] Envia mensagem. Exchange: Default | RoutingKey: {}", routingKey);
        log.debug("[MQ SENDER] Mensagem: {}", obj);
        rabbitTemplate.convertAndSend(routingKey, obj);
    }


}