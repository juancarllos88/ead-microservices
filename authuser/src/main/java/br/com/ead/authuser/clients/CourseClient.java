package br.com.ead.authuser.clients;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.ead.authuser.dto.CourseDto;
import br.com.ead.authuser.dto.ResponsePageDto;
import br.com.ead.authuser.services.impl.UtilsServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class CourseClient {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient webClient;

	@Autowired
	private UtilsServiceImpl utilsServiceImpl;
	
	@Value("${ead.api.url.course}")
	private String REQUEST_URI;
	
	private static Logger logg = LoggerFactory.getLogger(CourseClient.class);

	//@Retry(name = "retryInstance", fallbackMethod = "retryFallback")
	@CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "circuitBreakFallback")
	public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable, String token) {
		ResponseEntity<ResponsePageDto<CourseDto>> result = null;
        try{
        	ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            result = callUsingRestTemplate(userId, pageable, token, responseType);
        } catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
        } catch (Exception e){
            log.error("Erro to call service course ead");
            throw e;
        }
        log.info("Ending request /courses userId {} ", userId);
        return result.getBody();
    }
	
	private ResponseEntity<ResponsePageDto<CourseDto>> callUsingWebClient(UUID userId, Pageable pageable, String token, ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType) {
		Mono<ResponsePageDto<CourseDto>> result = webClient.method(HttpMethod.GET)
		         .uri(utilsServiceImpl.createUrlGetAllCoursesByUser(userId, pageable))
		         .retrieve()
		         .bodyToMono(responseType);
		
		ResponsePageDto<CourseDto> lisfOfCourseByUser = result.block();
		return  ResponseEntity.status(HttpStatus.OK).body(lisfOfCourseByUser);
		
	}
	
	
	private ResponseEntity<ResponsePageDto<CourseDto>> callUsingRestTemplate(UUID userId, Pageable pageable, String token, ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType) {
		String url = REQUEST_URI + utilsServiceImpl.createUrlGetAllCoursesByUser(userId, pageable);
		log.info("Calling service course ead: {} ", url);
		ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, buildHeader(token), responseType);
		List<CourseDto> searchResult = result.getBody().getContent();
        log.info("Response Number of Elements: {} ", searchResult.size());
		return result;
	}
	
	public HttpEntity<String> buildHeader(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		return new HttpEntity<String>("parameters",headers);
	}
	
	public Page<CourseDto> retryFallback(UUID userId, Pageable pageable, Throwable t) {
		log.error("Inside retry retryFallback, cause - {}", t.toString(),t);
		List<CourseDto> list = new ArrayList<>(); 
		return new PageImpl<>(list);
				 
	}
	
	public Page<CourseDto> circuitBreakFallback(UUID userId, Pageable pageable, String token, Throwable t) {
		log.error("circuit break open,s circuitBreakFallback, cause - {}", t.toString());
		List<CourseDto> list = new ArrayList<>();
		return new PageImpl<>(list);
				
	}

	public void deleteUserInCourse(UUID userId) {
		String url = REQUEST_URI + "/courses/users/" + userId;
		restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
	}
}
