package br.com.ead.course.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.ead.course.dto.commands.NotificationCommandDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class NotificationCommandPublisher {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value(value = "${ead.broker.exchange.notificationCommand}")
	private String exchangeNotificationCommand;
	
	@Value(value = "${ead.broker.routingKey.notificationCommand}")
	private String routingKeyNotificationCommand;

	public void publishNotificationCommand(NotificationCommandDto dto) {
		rabbitTemplate.convertAndSend(exchangeNotificationCommand, routingKeyNotificationCommand, dto);
	}
	
}
