package br.com.ead.notification.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class NotificationCommandDto {

	private String title;
	private UUID userId;
	private String message;

}
