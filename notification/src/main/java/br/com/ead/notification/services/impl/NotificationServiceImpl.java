package br.com.ead.notification.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ead.notification.dto.NotificationCommandDto;
import br.com.ead.notification.dto.NotificationDto;
import br.com.ead.notification.enums.Status;
import br.com.ead.notification.exceptions.NotificationNotFoundException;
import br.com.ead.notification.model.NotificationModel;
import br.com.ead.notification.repositories.NotificationRepository;
import br.com.ead.notification.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository repository;

	@Override
	public NotificationModel save(NotificationCommandDto NotificationCommandDto) {
		var notification = new NotificationModel();
		BeanUtils.copyProperties(NotificationCommandDto, notification);
		notification.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		notification.setStatus(Status.CREATED);
		return repository.save(notification);
	}

	@Override
	public Page<NotificationModel> findAllByUser(UUID userId, Pageable pageable) {
		return repository.findAllByUserIdAndStatus(userId, Status.CREATED, pageable);
	}

	@Override
	public NotificationModel changeStatusNotification(UUID userId, UUID notificationId,
			NotificationDto notificationDto) {
		Optional<NotificationModel> notificationModel = repository.findByIdAndUserId(notificationId, userId);
		var notification = notificationModel.orElseThrow(() -> new NotificationNotFoundException());
		notification.setStatus(notificationDto.getStatus());
		return repository.save(notification);
	}

}
