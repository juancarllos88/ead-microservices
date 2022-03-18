package br.com.ead.authuser.clients;

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

import br.com.ead.authuser.dto.CourseDto;
import br.com.ead.authuser.dto.ResponsePageDto;
import br.com.ead.authuser.services.impl.UtilsServiceImpl;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CourseClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UtilsServiceImpl utilsServiceImpl;
	
	@Value("${api.url.course}")
	private String REQUEST_URI;

	public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
		List<CourseDto> searchResult = null;
		String url = REQUEST_URI + utilsServiceImpl.createUrlGetAllCoursesByUser(userId, pageable);
		ResponseEntity<ResponsePageDto<CourseDto>> result = null;
        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try{
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId {} ", userId);
        return result.getBody();
    }

	public void deleteUserInCourse(UUID userId) {
		String url = REQUEST_URI + "/courses/users/" + userId;
		restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
	}
}
