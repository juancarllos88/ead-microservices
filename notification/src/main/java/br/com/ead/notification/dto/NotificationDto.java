package br.com.ead.notification.dto;

import javax.validation.constraints.NotNull;

import br.com.ead.notification.enums.Status;
import lombok.Data;

@Data
public class NotificationDto {

	@NotNull
	private Status status;
}
