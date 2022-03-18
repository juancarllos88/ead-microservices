package br.com.ead.course.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.ead.course.enums.CourseLevel;
import br.com.ead.course.enums.CourseStatus;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;

	@NotNull
	private CourseStatus status;
	
	@NotNull
	private CourseLevel level;
	
	@NotNull
	private UUID userInstructor;
	
	private String imageURL;

}
