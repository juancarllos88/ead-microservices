package br.com.ead.course.dto.commands;

import java.util.UUID;

import lombok.Data;

@Data
public class NotificationCommandDto {

	private String title;
	private UUID userId;
	private String message;

}
