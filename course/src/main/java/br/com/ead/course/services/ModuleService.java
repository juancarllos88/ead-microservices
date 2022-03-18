package br.com.ead.course.services;

import java.util.UUID;

import br.com.ead.course.dto.CourseDto;
import br.com.ead.course.dto.ModuleDto;
import br.com.ead.course.models.ModuleModel;

public interface ModuleService extends BaseService<ModuleModel>{
	
	ModuleModel save(UUID courseId, ModuleDto module);

	void delete(UUID courseId, UUID moduleId);

	ModuleModel update(UUID courseId, ModuleDto module, UUID moduleId);

}
