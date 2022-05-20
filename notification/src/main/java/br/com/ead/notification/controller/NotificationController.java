package br.com.ead.notification.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.notification.dto.NotificationCommandDto;
import br.com.ead.notification.dto.NotificationDto;
import br.com.ead.notification.model.NotificationModel;
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

	@GetMapping("/users/{userId}/notifications")
	public ResponseEntity<Page<NotificationModel>> findAllByUser(@PathVariable(value = "userId") UUID userId,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllByUser(userId, pageable));
	}

	@PatchMapping("/users/{userId}/notifications/{notificationId}")
	public ResponseEntity<NotificationModel> changeStatusNotification(@PathVariable(value = "userId") UUID userId,
			@PathVariable(value = "id") UUID notificationId, @RequestBody @Valid NotificationDto notificationDto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(notificationService.changeStatusNotification(userId, notificationId, notificationDto));
	}

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