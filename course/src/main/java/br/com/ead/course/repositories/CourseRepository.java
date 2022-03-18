package br.com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ead.course.models.CourseModel;

public interface CourseRepository extends BaseRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

}
