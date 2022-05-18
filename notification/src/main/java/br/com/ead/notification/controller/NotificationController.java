package br.com.ead.notification.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.notification.dto.NotificationCommandDto;
import br.com.ead.notification.publisher.CommandPublisher;
import br.com.ead.notification.services.NotificationService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    final NotificationService notificationService;
    
    @Autowired
    private CommandPublisher commandPublisher;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @GetMapping("/users/{userId}/notifications")
//    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(@PathVariable(value="userId") UUID userId,
//                                                                             @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable){
//        return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllNotificationsByUser(userId, pageable));
//    }
//
//    @PutMapping("/users/{userId}/notifications/{notificationId}")
//    public ResponseEntity<Object> updateNotification(@PathVariable(value="userId") UUID userId,
//                                                     @PathVariable(value="notificationId") UUID notificationId,
//                                                     @RequestBody @Valid NotificationDto notificationDto){
//        Optional<NotificationModel> notificationModelOptional =
//                notificationService.findByNotificationIdAndUserId(notificationId, userId);
//        if(notificationModelOptional.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
//        }
//        notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
//        notificationService.saveNotification(notificationModelOptional.get());
//        return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get());
//    }
    
    @GetMapping("/notifications/send")
	public String testMsg() {
    	var dto = new NotificationCommandDto();
    	dto.setUserId(UUID.randomUUID());
    	dto.setMessage("opa");
    	dto.setTitle("teste");
    	commandPublisher.publishUserEvent(dto);
		return "enviado";
	}


}