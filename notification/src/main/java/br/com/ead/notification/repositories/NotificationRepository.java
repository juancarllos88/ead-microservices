package br.com.ead.notification.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ead.notification.enums.Status;
import br.com.ead.notification.model.NotificationModel;

public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {

	Page<NotificationModel> findAllByUserIdAndStatus(UUID userId, Status status, Pageable pageable);

	Optional<NotificationModel> findByIdAndUserId(UUID id, UUID userId);
}
