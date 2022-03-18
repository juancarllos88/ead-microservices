package br.com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.ModuleModel;

public interface ModuleRepository  extends BaseRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

	@EntityGraph(attributePaths = {"course"})
	Optional<ModuleModel> findByTitle(String title);
	
	//@Modifying - para deletes e updates
	@Query(value = "select * from tb_modules where course_id = :courseId", nativeQuery = true)
	List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);
	
	@Query(value = "select * from tb_modules where course_id = :courseId and id= :moduleId", nativeQuery = true)
	Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);

	Optional<List<ModuleModel>> findByCourse(CourseModel courseModel);


}
