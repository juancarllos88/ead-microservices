package br.com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ead.course.models.UserModel;

public interface UserRepository extends BaseRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

}

