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
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.dto.LessonDto;
import br.com.ead.course.infrastructure.services.ResponseService;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.services.LessonService;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private ResponseService responseService;
	
	@PostMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<LessonModel> save(@PathVariable UUID moduleId, @RequestBody @Valid LessonDto lesson){
		return responseService.create(lessonService.save(moduleId, lesson));
	}
	
	@DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<String> delete(@PathVariable UUID moduleId, @PathVariable UUID lessonId){
		lessonService.delete(moduleId, lessonId);
		return responseService.ok("Course deleted sucessfully");
		
	}
	@PutMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<LessonModel> update(@PathVariable UUID moduleId, @RequestBody @Valid LessonDto lesson,@PathVariable UUID lessonId){
		return responseService.ok(lessonService.update(moduleId, lesson, lessonId));
	}
	
	@GetMapping("/modules/lessons")
	public ResponseEntity<Page<LessonModel>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return responseService.ok(lessonService.findAll(pageable));
	}
	
	@GetMapping("/modules/lessons/{lessonId}")
	public ResponseEntity<LessonModel> findOne(@PathVariable UUID lessonId) {
		return responseService.ok(lessonService.findOne(lessonId));
	}
	
	
	
	

}
