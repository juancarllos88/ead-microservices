package br.com.ead.course.infrastructure.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
	
	public <T> ResponseEntity<T> create(T data) {
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}
	
	public <T> ResponseEntity<T> ok(T data) {
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	public <T> ResponseEntity<T> notFound(T mensagem) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
	}

}
