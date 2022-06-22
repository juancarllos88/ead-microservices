package br.com.ead.authuser.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.ead.authuser.infrastructure.services.MessageServiceImpl;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected MessageServiceImpl mensagemService;
	
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		log.error("",ex);
		return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request, "internal.server.error");
	}
	
	@ExceptionHandler({ UserNotAuthorizedException.class })
	public ResponseEntity<Object> handleUserNotAuthorizedException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.FORBIDDEN, request, "user.not.authorized.exception");
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.FORBIDDEN, request, "access.denied");
	}
	
	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<Object> handleBadCredentialsException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.UNAUTHORIZED, request, "bad.credentials");
	}
	@ExceptionHandler({ SubscriptionAlreadyExistsException.class })
	public ResponseEntity<Object> handleSubscriptionAlreadyExistsException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.CONFLICT, request, "subscription.alread.exists");
	}
	

	@ExceptionHandler({ EntityModelNotFoundException.class })
	public ResponseEntity<Object> handleEntityModelNotFoundException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.NOT_FOUND, request, "entity.model.notfound");
	}
	
	@ExceptionHandler({ UserNameExistsException.class })
	public ResponseEntity<Object> handleUserNameExistsException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.CONFLICT, request, "user.username.exists");
	}
	
	@ExceptionHandler({ EmailExistsException.class })
	public ResponseEntity<Object> handleEmailExistsException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.CONFLICT, request, "user.email.exists");
	}
	
	@ExceptionHandler({ MismatchedPasswordException.class })
	public ResponseEntity<Object> handleMismatchedPasswordException(RuntimeException ex, WebRequest request) {
		return handleException(ex, HttpStatus.CONFLICT, request, "user.mismatched.password");
	}

	protected ResponseEntity<Object> handleException(Exception ex, HttpStatus status, WebRequest req, String chave) {
		List<String> errors = (Arrays.asList((mensagemService.getMessage(chave))));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), status, req);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> erros = obterListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	protected List<String> obterListaErros(BindingResult bindingResult) {
		List<String> erros = new ArrayList<>();
		bindingResult.getFieldErrors().forEach(e -> erros.add(e.getField() + " " + mensagemService.getMessage(e)));
		return erros;
	}

}
