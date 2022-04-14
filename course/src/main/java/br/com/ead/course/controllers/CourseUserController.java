package br.com.ead.course.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.dto.SubscriptionDto;
import br.com.ead.course.dto.UserDto;
import br.com.ead.course.infrastructure.services.ResponseService;
import br.com.ead.course.models.UserModel;
import br.com.ead.course.services.ConverterService;
import br.com.ead.course.services.CourseService;
import br.com.ead.course.services.UserService;
import br.com.ead.course.specification.SpecificationTemplate;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

	@Autowired
	private ResponseService responseService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ConverterService converterService;
	
	
	@Autowired
	private CourseService courseService;

	@GetMapping("/courses/{courseId}/users")
	public ResponseEntity<Page<UserDto>> getAllUserByCourse(SpecificationTemplate.UserSpec spec,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			@PathVariable(value = "courseId") UUID courseId) {
		Page<UserModel> users = userService.findAll(pageable, SpecificationTemplate.userCourseId(courseId).and(spec));
		return responseService.ok(converterService.convert(users, UserDto.class));

	}
	
	@PostMapping("/courses/{courseId}/users/subscription")
	public ResponseEntity<String> subscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody @Valid SubscriptionDto subscription){
		courseService.subscriptionUserInCourse(courseId,subscription);
		return responseService.create("Subscription created successfully.");
		
	}
	
}
