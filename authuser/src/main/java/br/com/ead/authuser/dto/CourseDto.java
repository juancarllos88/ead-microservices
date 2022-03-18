package br.com.ead.authuser.dto;

import java.io.Serializable;
import java.util.UUID;

import br.com.ead.authuser.enums.CourseLevel;
import br.com.ead.authuser.enums.CourseStatus;
import lombok.Data;

@Data
public class CourseDto implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String name;
	private String description;
	private String imageUrl;
	private CourseStatus status;
	private UUID userInstructor;
	private CourseLevel level;

}
