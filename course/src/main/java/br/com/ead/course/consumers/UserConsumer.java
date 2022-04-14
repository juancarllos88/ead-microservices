package br.com.ead.course.consumers;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.ead.course.dto.events.UserEventDto;
import br.com.ead.course.models.UserModel;
import br.com.ead.course.services.ConverterService;
import br.com.ead.course.services.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserConsumer {

    @Autowired
    UserService userService;
    
    @Autowired
    private ConverterService converterService;
    
    @Value(value = "${ead.broker.exchange.userEvent}")
	private String exchangeUserEvent;
    
    @Value(value = "${ead.broker.queue.userEvent.course}")
	private String queueUserEvent;


    @RabbitListener(concurrency = "1", bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEvent.course}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEvent}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload UserEventDto userEventDto){
    	log.info("Evento de {} lido da fila[{}] - exchange[{}] . Payload: {}", userEventDto.getActionType(), queueUserEvent, exchangeUserEvent, userEventDto);
        var userModel = converterService.convert(userEventDto, UserModel.class);

        switch (userEventDto.getActionType()){
            case CREATE:
            case UPDATE:
                userService.save(userModel);
                break;
            case DELETE:
                userService.delete(userEventDto.getId());
                break;
        }
    }
}

