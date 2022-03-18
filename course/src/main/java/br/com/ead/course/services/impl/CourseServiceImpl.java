package br.com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.ead.course.clients.UserClient;
import br.com.ead.course.dto.CourseDto;
import br.com.ead.course.dto.UserDto;
import br.com.ead.course.enums.UserStatus;
import br.com.ead.course.enums.UserType;
import br.com.ead.course.exceptions.ResourceNotFoundException;
import br.com.ead.course.exceptions.UserNotInstructorException;
import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.CourseUserModel;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
import br.com.ead.course.repositories.CourseRepository;
import br.com.ead.course.repositories.CourseUserRepository;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.repositories.ModuleRepository;
import br.com.ead.course.services.CourseService;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseModel> implements CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	private CourseUserRepository courseUserRepository;
	
	@Autowired
	private UserClient userClient;

	@Override
	protected CourseRepository getRepository() {
		return courseRepository;
	}

	@Override
	protected CourseModel getEntityModel() {
		return new CourseModel();
	}
	
	
	@Override
	@Transactional
	public void delete(UUID id) {
		CourseModel courseModel = findOne(id);
		Optional<List<ModuleModel>> modules = moduleRepository.findByCourse(courseModel);
		if (modules.isPresent()) {
			modules.get().forEach(module -> {
				Optional<List<LessonModel>> lessons = lessonRepository.findByModule(module);
				if (lessons.isPresent()) {
					lessonRepository.deleteAll(lessons.get());
				}
			});
			moduleRepository.deleteAll(modules.get());
		}
		Optional<List<CourseUserModel>> courseModels = courseUserRepository.findByCourse(courseModel);
		if(courseModels.isPresent()) {
			courseUserRepository.deleteAll(courseModels.get());
		}
		getRepository().delete(courseModel);
		userClient.deleteCourseInAuthuser(id);
	}

	@Override
	@Transactional
	public Page<CourseModel> findAll(Pageable pageable, Specification<CourseModel> specification) {
		return courseRepository.findAll(specification,pageable);
	}
	

	@Override
	@Transactional
	public CourseModel save(CourseDto dto) {
		CourseModel entityModel = getEntityModel();
		try {
			UserDto userDto = userClient.getUserById(dto.getUserInstructor());
			if (!userDto.getStatus().equals(UserStatus.ACTIVE) || userDto.getType().equals(UserType.STUDENT)) {
				throw new UserNotInstructorException();
			}
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new ResourceNotFoundException("User " + dto.getUserInstructor());
			}
		}
		BeanUtils.copyProperties(dto, entityModel);
		return courseRepository.save(entityModel);
	}

}
