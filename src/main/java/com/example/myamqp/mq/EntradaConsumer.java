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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class EntradaConsumer {

    private final Logger log = LoggerFactory.getLogger(EntradaConsumer.class);
    static private String QUEUE_KEY = "entrada";
    private QueueConfig queueConfig;

    @Autowired private Passo2Sender passo2Sender;

    public EntradaConsumer(AppMqConfig appMqConfig) {
        if (QUEUE_KEY != null) {
            this.queueConfig = appMqConfig.getQueues().get(QUEUE_KEY);
        }
    }

    @Bean
    public Queue criaEntradaQueue() {
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

    @RabbitListener(queues = { "#{ entradaConsumer.queueConfig.name }" })
    public void onMessage(Message message, Channel channel) throws IOException {
        log.debug("[MQ RECEIVER] Nova Mensagem. | queue: {} | message: {}", queueConfig.getName(), message);
        try {
           String entrada = new String(message.getBody());
           log.info("[MQ RECEIVER] Mensagem de entrada '{}'", entrada);

           DadoDto obj = new DadoDto();
           obj.setOriginal(entrada);
           passo2Sender.send(obj);
           
           channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);           
        } catch (Exception e) {
           log.error("[MQ RECEIVER] Não foi possível processar a mensagem {}. Erro: {}", message.getBody(), e.getMessage(), e);
           channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
        log.debug("[MQ RECEIVER] Mensagem Processada. | queue: {} | message: {}", queueConfig.getName(), message);
     }
    
}