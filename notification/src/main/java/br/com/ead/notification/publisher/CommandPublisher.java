package br.com.ead.notification.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.ead.notification.dto.NotificationCommandDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CommandPublisher {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value(value = "${ead.broker.exchange.notificationCommand}")
	private String exchangeNotificationCommand;

	public void publishUserEvent(NotificationCommandDto dto) {
		rabbitTemplate.convertAndSend(exchangeNotificationCommand, "ms.subscription.notification", dto);
		log.info("Command enviado para o exchange");
	}
	
}
