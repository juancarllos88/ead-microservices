package br.com.ead.course.services;

import java.util.UUID;

import br.com.ead.course.dto.LessonDto;
import br.com.ead.course.models.LessonModel;

public interface LessonService extends BaseService<LessonModel>{

	LessonModel save(UUID moduleId, LessonDto lesson);

	void delete(UUID moduleId, UUID lessonId);

	LessonModel update(UUID moduleId, LessonDto lesson, UUID lessonId);

}
