package br.com.ead.authuser.controllers;

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

import br.com.ead.authuser.clients.CourseClient;
import br.com.ead.authuser.dto.CourseDto;
import br.com.ead.authuser.dto.SubscriptionDto;
import br.com.ead.authuser.infrastructure.services.ResponseService;
import br.com.ead.authuser.models.UserCourseModel;
import br.com.ead.authuser.services.UserCourseService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

	@Autowired
	private ResponseService responseService;

	@Autowired
	private CourseClient courseClient;
	
	@Autowired
	private UserCourseService userCourseService;

	@GetMapping("/users/{userId}/courses")
	public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			@PathVariable(value = "userId") UUID userId) {
		return responseService.ok(courseClient.getAllCoursesByUser(userId, pageable));

	}
	
	@PostMapping("/users/{userId}/courses/subscription")
	public ResponseEntity<UserCourseModel> subscriptionUserInCourse(@PathVariable UUID userId, @RequestBody @Valid SubscriptionDto subscription){
		UserCourseModel userCourseModel = userCourseService.subscriptionUserInCourse(userId,subscription);
		return responseService.create(userCourseModel);
	}
	
	@DeleteMapping("/users/courses/{courseId}")
	public ResponseEntity<String> deleteUserCourseByCourse(@PathVariable UUID courseId){
		userCourseService.deleteUserCourseByCourse(courseId);
		return responseService.ok("UserCourse deleted sucessfully");
	}
	
	

}
