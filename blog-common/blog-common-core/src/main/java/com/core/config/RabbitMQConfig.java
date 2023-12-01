package com.core.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.api.constant.RabbitMQConstant.*;


/**
 * @author xiongke
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue articleQueue() {
        return new Queue(MAXWELL_QUEUE, true);
    }

    @Bean
    public FanoutExchange maxWellExchange() {
        return ExchangeBuilder.fanoutExchange(MAXWELL_EXCHANGE).build();
    }

    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

    @Bean
    public Queue subscribeQueue() {
        return new Queue(SUBSCRIBE_QUEUE, true);
    }

    @Bean
    public FanoutExchange subscribeExchange() {
        return new FanoutExchange(SUBSCRIBE_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingSubscribeDirect() {
        return BindingBuilder.bind(subscribeQueue()).to(subscribeExchange());
    }

}
