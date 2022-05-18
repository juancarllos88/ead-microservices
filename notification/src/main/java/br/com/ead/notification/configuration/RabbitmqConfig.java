package br.com.ead.notification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitmqConfig {
	
	@Value(value = "${ead.broker.exchange.notificationCommand}")
	private String exchangeNotificationCommand;
	
	@Value(value = "${ead.broker.exchange.deadNotificationCommand}")
	private String deadLetterExchangeNotificationCommand;
	
	@Value(value = "${ead.broker.queue.notificationCommand}")
	private String queueNotificationCommand;
	
	@Value(value = "${ead.broker.queue.deadNotificationCommand}")
	private String deadLetterQueueNotificationCommand;

	@Value(value ="${ead.broker.key.notificationCommandRoutingKey}")
	private String notificationCommandRoutingKey;
	
    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    
    
	@Bean
	public TopicExchange topicNotificationCommand() {
		return new TopicExchange(exchangeNotificationCommand);
	}
	
	@Bean
	public DirectExchange directDeadLetterNotificationCommand() {
		return new DirectExchange(deadLetterExchangeNotificationCommand);
	}
	
	@Bean
	public Queue deadLetterQueueNotificationCommand() {
		return QueueBuilder.durable(deadLetterQueueNotificationCommand).build();
	}
	

	@Bean
	public Queue queueNotificationCommand() {
		return QueueBuilder.durable(queueNotificationCommand)
				.withArgument("x-dead-letter-exchange", deadLetterExchangeNotificationCommand)
				.withArgument("x-dead-letter-routing-key", "deadLetter")
				.build();
	}
	
	@Bean
	Binding deadLetternotificationCommandBinding() {
		return BindingBuilder.bind(deadLetterQueueNotificationCommand()).to(directDeadLetterNotificationCommand()).with("deadLetter");
	}

	@Bean
	Binding notificationCommandBinding() {
		return BindingBuilder.bind(queueNotificationCommand()).to(topicNotificationCommand()).with(notificationCommandRoutingKey);
	}
	
    
    
    
	
	

}
