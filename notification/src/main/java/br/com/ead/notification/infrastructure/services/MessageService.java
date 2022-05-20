package br.com.ead.notification.infrastructure.services;

import org.springframework.validation.FieldError;

public interface MessageService {

	public String getMessage(String key);
	
	public String getMessage(FieldError fieldError);
}
