package br.com.ead.course.services.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.ead.course.dto.LessonDto;
import br.com.ead.course.exceptions.EntityModelNotFoundException;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.services.LessonService;
import br.com.ead.course.services.ModuleService;

@Service
public class LessonServiceImpl extends BaseServiceImpl<LessonModel>  implements LessonService{

	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	private ModuleService moduleService;

	@Override
	protected LessonRepository getRepository() {
		return lessonRepository;
	}

	@Override
	protected LessonModel getEntityModel() {
		return new LessonModel();
	}
	
	@Override
	public LessonModel save(UUID moduleId, LessonDto lesson) {
		 var moduleModel = moduleService.findOne(moduleId);
		 var lessonModel = new LessonModel();
		 BeanUtils.copyProperties(lesson, lessonModel);
		 lessonModel.setModule(moduleModel);
		 return getRepository().save(lessonModel);
	}

	@Override
	public void delete(UUID moduleId, UUID lessonId) {
		var lessonModel = getRepository().findLessonIntoModule(moduleId, lessonId)
				.orElseThrow(() -> new EntityModelNotFoundException());
		getRepository().delete(lessonModel);
	}

	@Override
	public LessonModel update(UUID moduleId, LessonDto lesson, UUID lessonId) {
		getRepository().findLessonIntoModule(moduleId, lessonId)
				.orElseThrow(() -> new EntityModelNotFoundException());
		return update(lessonId, lesson);
	}
	
	@Override
	public Page<LessonModel> findAll(Pageable pageable, Specification<LessonModel> specification) {
		return lessonRepository.findAll(specification,pageable);
	}

	
}
