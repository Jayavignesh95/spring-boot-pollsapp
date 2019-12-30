package com.poll.pollsapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String MAIL_QUEUE = "emailQueue";

	public static final String MAIL_EXCHANGE = "emailExchange";

	@Bean
	Queue mailQueue() {
		return QueueBuilder.durable(MAIL_QUEUE).build();
	}

	@Bean
	Exchange mailExchange() {
		return ExchangeBuilder.topicExchange(MAIL_EXCHANGE).build();
	}

	@Bean
	Binding binding(Queue mailQueue, TopicExchange mailExchange) {
		return BindingBuilder.bind(mailQueue).to(mailExchange).with(MAIL_QUEUE);
	}
	
	@Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
 
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
