package com.br.VotaCoop.core.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "Contrabilização de votos";

    @Bean
    public Queue queue() {
        return new org.springframework.amqp.core.Queue(QUEUE_NAME, false);
    }
}
