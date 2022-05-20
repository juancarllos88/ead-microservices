package br.com.ead.notification.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.ead.notification.infrastructure.services.MessageService;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected MessageService mensagemService;
	
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		log.error("",ex);
		return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request, "internal.server.error");
	}
	

	@ExceptionHandler({ NotificationNotFoundException.class })
	public ResponseEntity<Object> handleNotificationNotFoundException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.NOT_FOUND, request, "notification.model.notfound");
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> erros = getErrorList(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	protected List<String> getErrorList(BindingResult bindingResult) {
		List<String> erros = new ArrayList<>();
		bindingResult.getFieldErrors().forEach(e -> erros.add(e.getField() + " " + mensagemService.getMessage(e)));
		return erros;
	}

	protected ResponseEntity<Object> handleException(Exception ex, HttpStatus status, WebRequest req, String chave) {
		List<String> errors = (Arrays.asList((mensagemService.getMessage(chave))));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), status, req);
	}
}
