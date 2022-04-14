package br.com.ead.authuser.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.ead.authuser.dto.events.UserEventDto;
import br.com.ead.authuser.enums.ActionType;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserEventPublisher {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value(value = "${ead.broker.exchange.userEvent}")
	private String exchangeUserEvent;

	public void publishUserEvent(UserEventDto userEventDto, ActionType actionType) {
		userEventDto.setActionType(actionType);
		rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);
		log.info("Evento de {} enviado para o exchange {} . Payload: {}", actionType, exchangeUserEvent, userEventDto);
	}

}
