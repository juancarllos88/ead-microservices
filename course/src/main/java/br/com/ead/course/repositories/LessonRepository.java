package br.com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;

public interface LessonRepository extends BaseRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {
	
	@Query(value = "select * from tb_lessons where module_id = :moduleId and id= :lessonId", nativeQuery = true)
	Optional<LessonModel> findLessonIntoModule(@Param("moduleId") UUID courseId, @Param("lessonId") UUID lessonId);

	Optional<List<LessonModel>> findByModule(ModuleModel module);

}
