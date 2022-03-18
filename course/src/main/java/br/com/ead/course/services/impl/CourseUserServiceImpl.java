package br.com.ead.course.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.ead.course.clients.UserClient;
import br.com.ead.course.dto.SubscriptionDto;
import br.com.ead.course.dto.UserDto;
import br.com.ead.course.enums.UserStatus;
import br.com.ead.course.exceptions.ResourceNotFoundException;
import br.com.ead.course.exceptions.SubscriptionAlreadyExistsException;
import br.com.ead.course.exceptions.UserBlockedException;
import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.CourseUserModel;
import br.com.ead.course.repositories.CourseUserRepository;
import br.com.ead.course.services.CourseService;
import br.com.ead.course.services.CourseUserService;

@Service
public class CourseUserServiceImpl extends BaseServiceImpl<CourseUserModel> implements CourseUserService {

	@Autowired
	private CourseUserRepository courseUserRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserClient userClient;


	@Override
	protected CourseUserRepository getRepository() {
		return courseUserRepository;
	}

	@Override
	protected CourseUserModel getEntityModel() {
		return new CourseUserModel();
	}

	@Override
	public Page<CourseUserModel> findAll(Pageable pageable, Specification<CourseUserModel> specification) {
		return courseUserRepository.findAll(specification,pageable);
	}

	@Override
	@Transactional
	public CourseUserModel subscriptionUserInCourse(UUID courseId, SubscriptionDto subscription) {
		CourseModel courseModel = courseService.findOne(courseId);
		if(getRepository().existsByCourseAndUserId(courseModel, subscription.getUserId())) {
			throw new SubscriptionAlreadyExistsException();
		}
		try {
			UserDto userDto = userClient.getUserById(subscription.getUserId());
			  if(userDto.getStatus().equals(UserStatus.BLOCKED)){
	                throw new UserBlockedException();
	            }
		}catch (HttpStatusCodeException e) {
			if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new ResourceNotFoundException("User " + subscription.getUserId());
			}
		}
		var courseUserModel = getRepository().save(CourseUserModel.convertToCourseUserModel(courseModel, subscription.getUserId()));
		userClient.subscriptionUserInCourse(courseId, subscription.getUserId());
		return courseUserModel;
	}

	@Override
	public void deleteCourseUserByUser(UUID userId) {
		List<CourseUserModel> courseUserModels  = courseUserRepository.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("CourseUserModel"));
		courseUserRepository.deleteAll(courseUserModels);
	}

	
	

}
