package br.com.ead.course.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.ead.course.dto.ResponsePageDto;
import br.com.ead.course.dto.SubscriptionUserCourseDto;
import br.com.ead.course.dto.UserDto;
import br.com.ead.course.services.impl.UtilsServiceImpl;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UtilsServiceImpl utilsServiceImpl;
	
	@Value("${ead.api.url.authuser}")
	private String REQUEST_URI;

	public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
		List<UserDto> searchResult = null;
		ResponseEntity<ResponsePageDto<UserDto>> result = null;
		String url = REQUEST_URI + utilsServiceImpl.createUrlGetAllUsersByCourse(courseId, pageable);
		log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
		try {
			ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {};
			result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
			searchResult = result.getBody().getContent();
			log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return result.getBody();

	}
	
	public UserDto getUserById(UUID userId) {
		ResponseEntity<UserDto> result = null;
		String url = REQUEST_URI + "/users/" + userId;
		log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
		try {
			result = restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
        } catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
            throw e;
        }
        log.info("Ending request /users/{} ", userId);
        return result.getBody();

	}
	
	public void subscriptionUserInCourse(UUID courseId, UUID userId) {
		String url = REQUEST_URI + "/users/" + userId + "/courses/subscription";
		log.debug("Request URL: {} ", url);
		log.info("Request URL: {} ", url);
		var subscriptionUserCourseDto = new SubscriptionUserCourseDto();
		subscriptionUserCourseDto.setCourseId(courseId);
		restTemplate.postForObject(url, subscriptionUserCourseDto, String.class);
	}
	
	public void deleteCourseInAuthuser(UUID courseId) {
		String url = REQUEST_URI + "/users/courses/" + courseId;
		restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
	}

}
