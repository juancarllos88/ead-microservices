package br.com.ead.authuser.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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

	@Autowired
	CachingConnectionFactory cachingConnectionFactory;

	@Value(value = "${ead.broker.exchange.userEvent}")
	private String exchangeUserEvent;
	
	@Value(value = "${ead.broker.queue.userEvent.course}")
	private String queueUserEventCourse;

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
	public FanoutExchange fanoutUserEvent() {
		return new FanoutExchange(exchangeUserEvent);
	}
	
//	@Bean
//	public Queue queueUserEventCourse() {
//		return new Queue(queueUserEventCourse, false);
//	}
	
//	@Bean
//	public Queue queueUserEventTeste() {
//		return new Queue("ead.userevent.ms.teste", false);
//	}
	
//	@Bean
//	Binding userEventCourseBinding(Queue queueUserEventCourse, FanoutExchange fanoutUserEvent) {
//		return new Binding(queueUserEventCourse.getName(), Binding.DestinationType.QUEUE, fanoutUserEvent.getName(), "", null);
//	}
	
//	@Bean
//	Binding userEventTesteBinding(Queue queueUserEventTeste, FanoutExchange fanoutUserEvent) {
//		return new Binding(queueUserEventTeste.getName(), Binding.DestinationType.QUEUE, fanoutUserEvent.getName(), "", null);
//	}

}
