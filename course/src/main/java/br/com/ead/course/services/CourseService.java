package br.com.ead.course.services;

import java.util.UUID;

import br.com.ead.course.dto.CourseDto;
import br.com.ead.course.dto.SubscriptionDto;
import br.com.ead.course.models.CourseModel;

public interface CourseService extends BaseService<CourseModel>{

	CourseModel save(CourseDto dto);

	void subscriptionUserInCourse(UUID courseId, SubscriptionDto subscription);



}
