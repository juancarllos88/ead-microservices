package br.com.ead.authuser.services;

import java.util.UUID;

import br.com.ead.authuser.dto.SubscriptionDto;
import br.com.ead.authuser.models.UserCourseModel;

public interface UserCourseService {

	UserCourseModel subscriptionUserInCourse(UUID userId, SubscriptionDto subscription);

	void deleteUserCourseByCourse(UUID courseId);


}
