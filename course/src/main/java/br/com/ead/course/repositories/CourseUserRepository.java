package br.com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.CourseUserModel;

public interface CourseUserRepository extends BaseRepository<CourseUserModel, UUID>, JpaSpecificationExecutor<CourseUserModel> {
	
	boolean existsByCourseAndUserId(CourseModel course, UUID userId);
	
	Optional<List<CourseUserModel>> findByCourse(CourseModel course);

	Optional<List<CourseUserModel>> findByUserId(UUID userId);

}
