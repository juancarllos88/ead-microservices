package br.com.ead.authuser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ead.authuser.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

	@EntityGraph(attributePaths = "roles", type= EntityGraph.EntityGraphType.FETCH)
	Optional<UserModel> findByUserName(String userName);
	
	Optional<UserModel> findByEmail(String email);

}
