package com.example.myamqp.mq;

import java.io.IOException;

import com.example.myamqp.config.AppMqConfig;
import com.example.myamqp.config.AppMqConfig.QueueConfig;
import com.rabbitmq.client.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Passo2Consumer {

    private final Logger log = LoggerFactory.getLogger(Passo2Consumer.class);
    static private String QUEUE_KEY = "passo2";
    private QueueConfig queueConfig;

    public Passo2Consumer(AppMqConfig appMqConfig) {
        if (QUEUE_KEY != null) {
            this.queueConfig = appMqConfig.getQueues().get(QUEUE_KEY);
        }
    }

    @Bean
    public Queue criaPasso2Queue() {
        if (queueConfig.getAutoCreate()) {
            log.info("[RabbitMQ] Criando fila '{}'", queueConfig.getName());
            return new Queue(queueConfig.getName());
        } else {
            return null;
        }
    }

    public QueueConfig getQueueConfig() {
        return queueConfig;
    }

    @RabbitListener(queues = { "#{ passo2Consumer.queueConfig.name }" })
    public void onMessage(DadoDto dado, Message message, Channel channel) throws IOException {
        log.debug("[MQ RECEIVER] Nova Mensagem. | queue: {} | message: {}", queueConfig.getName(), message);
        try {
           log.info("[MQ RECEIVER] Passo2. Objeto: {}", dado);

           dado.setMaisculo(dado.getOriginal().toUpperCase());
           log.info("OBJETO PROCESSADO: {}", dado);

           channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
           log.error("[MQ RECEIVER] Não foi possível processar a mensagem {}. Erro: {}", message.getBody(), e.getMessage(), e);
           channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
        log.debug("[MQ RECEIVER] Mensagem Processada. | queue: {} | message: {}", queueConfig.getName(), message);
     }
    
}