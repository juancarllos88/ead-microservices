package br.com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.ead.course.dto.ModuleDto;
import br.com.ead.course.exceptions.EntityModelNotFoundException;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.repositories.ModuleRepository;
import br.com.ead.course.services.CourseService;
import br.com.ead.course.services.ModuleService;

@Service
public class ModuleServiceImpl extends BaseServiceImpl<ModuleModel> implements ModuleService{
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private LessonRepository lessonRepository;

	@Override
	protected ModuleRepository getRepository() {
		return moduleRepository;
	}

	@Override
	protected ModuleModel getEntityModel() {
		return new ModuleModel();
	}

	@Override
	public ModuleModel save(UUID courseId, ModuleDto module) {
		 var courseModel = courseService.findOne(courseId);
		 var moduleModule = new ModuleModel();
		 BeanUtils.copyProperties(module, moduleModule);
		 moduleModule.setCourse(courseModel);
		 return getRepository().save(moduleModule);
	}

	@Override
	public void delete(UUID courseId, UUID moduleId) {
		var moduleModel = getRepository().findModuleIntoCourse(courseId, moduleId)
				.orElseThrow(() -> new EntityModelNotFoundException());
		Optional<List<LessonModel>> lessons = lessonRepository.findByModule(moduleModel);
		if (lessons.isPresent()) {
			lessonRepository.deleteAll(lessons.get());
		}
		getRepository().delete(moduleModel);
	}

	@Override
	public ModuleModel update(UUID courseId, ModuleDto module, UUID moduleId) {
		getRepository().findModuleIntoCourse(courseId, moduleId)
				.orElseThrow(() -> new EntityModelNotFoundException());
		return update(moduleId, module);
	}
	
	@Override
	public Page<ModuleModel> findAll(Pageable pageable, Specification<ModuleModel> specification) {
		return moduleRepository.findAll(specification,pageable);
	}

}
