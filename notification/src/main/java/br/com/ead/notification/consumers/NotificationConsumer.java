package br.com.ead.notification.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.ead.notification.dto.NotificationCommandDto;
import br.com.ead.notification.services.NotificationService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class NotificationConsumer {

	final NotificationService notificationService;

	public NotificationConsumer(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
//	@RabbitListener(bindings = @QueueBinding(
//			value = @Queue(value = "${ead.broker.queue.notificationCommand}", durable = "true"),
//			exchange = @Exchange(value = "${ead.broker.exchange.notificationCommand}",type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
//			key = "${ead.broker.key.notificationCommandRoutingKey}"))
	@RabbitListener(queues = "${ead.broker.queue.notificationCommand}")
	public void listen(@Payload NotificationCommandDto notificationDto) throws RuntimeException{
		//throw new RuntimeException("deu merda");
		log.info("Message command read to subscription user in courser {}", notificationDto.toString());
		notificationService.save(notificationDto);
	}
	
	@RabbitListener(queues = "${ead.broker.queue.deadNotificationCommand}")
	public void processFailedMessages(@Payload NotificationCommandDto notificationDto) {
		log.info("Message command processed from dead letter queue {}", notificationDto.toString());
	}

}
