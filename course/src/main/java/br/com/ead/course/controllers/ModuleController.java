package br.com.ead.course.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

import br.com.ead.course.dto.ModuleDto;
import br.com.ead.course.infrastructure.services.ResponseService;
import br.com.ead.course.models.ModuleModel;
import br.com.ead.course.services.ModuleService;
import br.com.ead.course.specification.SpecificationTemplate;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ResponseService responseService;
	
	@PostMapping("/courses/{courseId}/modules")
	public ResponseEntity<ModuleModel> save(@PathVariable UUID courseId, @RequestBody @Valid ModuleDto module){
		return responseService.create(moduleService.save(courseId, module));
	}
	
	@DeleteMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<String> delete(@PathVariable UUID courseId, @PathVariable UUID moduleId){
		moduleService.delete(courseId, moduleId);
		return responseService.ok("Course deleted sucessfully");
		
	}
	@PutMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<ModuleModel> update(@PathVariable UUID courseId, @RequestBody @Valid ModuleDto module,@PathVariable UUID moduleId){
		return responseService.ok(moduleService.update(courseId, module, moduleId));
	}
	
	@GetMapping("/courses/modules")
	public ResponseEntity<Page<ModuleModel>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			Specification<ModuleModel> specification) {
		return responseService.ok(moduleService.findAll(pageable,specification));
	  //return responseService.ok(moduleService.findAll(pageable,SpecificationTemplate.moduleCourseId(new UUID(1, 1)).and(specification)));
	}
	
	@GetMapping("/courses/modules/{moduleId}")
	public ResponseEntity<ModuleModel> findOne(@PathVariable UUID moduleId) {
		return responseService.ok(moduleService.findOne(moduleId));
	}
	
	
	
	

}
