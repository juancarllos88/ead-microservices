package br.com.ead.authuser.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ead.authuser.models.UserCourseModel;
import br.com.ead.authuser.models.UserModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID>, JpaSpecificationExecutor<UserCourseModel> {

	boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

	Optional<List<UserCourseModel>> findByCourseId(UUID courseId);

	Optional<List<UserCourseModel>> findByUser(UserModel user);


}
