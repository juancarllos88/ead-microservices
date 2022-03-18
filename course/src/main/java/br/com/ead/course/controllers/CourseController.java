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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.dto.CourseDto;
import br.com.ead.course.infrastructure.services.ResponseService;
import br.com.ead.course.models.CourseModel;
import br.com.ead.course.services.CourseService;
import br.com.ead.course.specification.SpecificationTemplate;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ResponseService responseService;
	
	@PostMapping()
	public ResponseEntity<CourseModel> save(@RequestBody @Valid CourseDto course){
		return responseService.create(courseService.save(course));
	}
	
	@DeleteMapping("/{courseId}")
	public ResponseEntity<String> delete(@PathVariable UUID courseId){
		courseService.delete(courseId);
		return responseService.ok("Course deleted sucessfully");
		
	}
	
	@GetMapping("/{courseId}")
	public ResponseEntity<CourseModel> findOne(@PathVariable UUID courseId){
		var courseModel = courseService.findOne(courseId);
		return responseService.ok(courseModel);
	}
	
	@GetMapping
	public ResponseEntity<Page<CourseModel>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			SpecificationTemplate.CourseSpec specification, @RequestParam(required = false) UUID userId) {
		Page<CourseModel> courseModelPage = courseService.findAll(pageable,
				userId != null ? SpecificationTemplate.courseUserId(userId).and(specification) : specification);
		return responseService.ok(courseModelPage);
	}
	
	@PutMapping("/{courseId}")
	public ResponseEntity<CourseModel> update(@PathVariable UUID courseId, @RequestBody @Valid CourseDto course){
		var courseModel = courseService.update(courseId, course);
		return responseService.ok(courseModel);
	}
	
	

}
