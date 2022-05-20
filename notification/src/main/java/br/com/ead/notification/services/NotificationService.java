package br.com.ead.notification.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ead.notification.dto.NotificationCommandDto;
import br.com.ead.notification.dto.NotificationDto;
import br.com.ead.notification.model.NotificationModel;

public interface NotificationService {

	public NotificationModel save(NotificationCommandDto notificationDto);

	public Page<NotificationModel> findAllByUser(UUID userId, Pageable pageable);

	public NotificationModel changeStatusNotification(UUID userId, UUID notificationId,
			NotificationDto notificationDto);

}
