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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.clients.UserClient;
import br.com.ead.course.dto.SubscriptionDto;
import br.com.ead.course.dto.UserDto;
import br.com.ead.course.infrastructure.services.ResponseService;
import br.com.ead.course.models.CourseUserModel;
import br.com.ead.course.services.CourseUserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

	@Autowired
	private ResponseService responseService;

	@Autowired
	private UserClient userClient;
	
	
	@Autowired
	private CourseUserService courseUserService;

	@GetMapping("/courses/{courseId}/users")
	public ResponseEntity<Page<UserDto>> getAllCoursesByUser(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			@PathVariable(value = "courseId") UUID courseId) {
		return responseService.ok(userClient.getAllUsersByCourse(courseId, pageable));

	}
	
	@PostMapping("/courses/{courseId}/users/subscription")
	public ResponseEntity<CourseUserModel> subscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody @Valid SubscriptionDto subscription){
		CourseUserModel courseUserModel = courseUserService.subscriptionUserInCourse(courseId,subscription);
		return responseService.create(courseUserModel);
		
	}
	
	@DeleteMapping("/courses/users/{userId}")
	public ResponseEntity<String> deleteCourseUserByUser(@PathVariable UUID userId){
		courseUserService.deleteCourseUserByUser(userId);
		return responseService.ok("UserCourse deleted sucessfully");
	}

}
