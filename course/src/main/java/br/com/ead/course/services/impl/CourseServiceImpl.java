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
import org.springframework.transaction.annotation.Transactional;

import br.com.ead.course.clients.UserClient;
import br.com.ead.course.dto.CourseDto;
import br.com.ead.course.dto.SubscriptionDto;
import br.com.ead.course.enums.UserStatus;
import br.com.ead.course.enums.UserType;
import br.com.ead.course.exceptions.SubscriptionAlreadyExistsException;
import br.com.ead.course.exceptions.UserBlockedException;
import br.com.ead.course.exceptions.UserNotInstructorException;
import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
import br.com.ead.course.models.UserModel;
import br.com.ead.course.repositories.CourseRepository;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.repositories.ModuleRepository;
import br.com.ead.course.services.CourseService;
import br.com.ead.course.services.UserService;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseModel> implements CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private LessonRepository lessonRepository;
	
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private UserService userService;

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
		courseRepository.deleteCourseUserByCourse(courseModel.getId());
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
		UserModel user = userService.findOne(dto.getUserInstructor());
		if (!user.getStatus().equals(UserStatus.ACTIVE) || user.getType().equals(UserType.STUDENT)) {
			throw new UserNotInstructorException();
		}
		BeanUtils.copyProperties(dto, entityModel);
		return courseRepository.save(entityModel);
	}


	@Override
	@Transactional
	public void subscriptionUserInCourse(UUID courseId, SubscriptionDto subscription) {
		findOne(courseId);
		if (getRepository().existsByCourseAndUser(courseId, subscription.getUserId())) {
			throw new SubscriptionAlreadyExistsException();
		}
		UserModel userModel = userService.findOne(subscription.getUserId());
		if (userModel.getStatus().equals(UserStatus.BLOCKED)) {
			throw new UserBlockedException();
		}
		getRepository().saveCourseUser(courseId, userModel.getId());
	}


}
