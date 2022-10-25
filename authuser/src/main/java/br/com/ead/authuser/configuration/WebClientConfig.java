package br.com.ead.authuser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Value("${ead.api.url.course}")
	private String REQUEST_URI_COURSE;
	
	
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl(REQUEST_URI_COURSE)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}
