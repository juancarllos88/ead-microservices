package br.com.ead.authuser.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ApplicationConfiguration {

	@Bean
	@RequestScope
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
