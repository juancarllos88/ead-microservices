package br.com.ead.authuser.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.authuser.dto.SubscriptionDto;
import br.com.ead.authuser.exceptions.EntityModelNotFoundException;
import br.com.ead.authuser.exceptions.SubscriptionAlreadyExistsException;
import br.com.ead.authuser.models.UserCourseModel;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.repositories.UserCourseRepository;
import br.com.ead.authuser.services.UserCourseService;
import br.com.ead.authuser.services.UserService;

@Service
public class UserCourseServiceImpl implements UserCourseService {
	
	@Autowired
	private UserService userService;

	private final UserCourseRepository userCourseRepository;

	public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
		this.userCourseRepository = userCourseRepository;
	}

	@Override
	public UserCourseModel subscriptionUserInCourse(UUID userId, SubscriptionDto subscription) {
		UserModel userModel = userService.findOne(userId);

		if (userCourseRepository.existsByUserAndCourseId(userModel, subscription.getCourseId())) {
			throw new SubscriptionAlreadyExistsException();
		}

		return userCourseRepository
				.save(UserCourseModel.convertToUserCourseModel(userModel, subscription.getCourseId()));
	}

	@Override
	public void deleteUserCourseByCourse(UUID courseId) {
		List<UserCourseModel> userCourseModels  = userCourseRepository.findByCourseId(courseId).orElseThrow(()-> new EntityModelNotFoundException());
		userCourseRepository.deleteAll(userCourseModels);
	}

}
