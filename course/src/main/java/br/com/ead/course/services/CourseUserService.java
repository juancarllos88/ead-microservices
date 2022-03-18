package br.com.ead.course.services;

import java.util.UUID;

import br.com.ead.course.dto.SubscriptionDto;
import br.com.ead.course.models.CourseUserModel;

public interface CourseUserService extends BaseService<CourseUserModel>{

	CourseUserModel subscriptionUserInCourse(UUID courseId, SubscriptionDto subscription);

	void deleteCourseUserByUser(UUID userId);



}
