package br.com.ead.authuser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.ead.authuser.enums.RoleType;
import br.com.ead.authuser.models.RoleModel;
import br.com.ead.authuser.models.UserModel;

public interface RoleRepository extends JpaRepository<RoleModel, UUID>, JpaSpecificationExecutor<UserModel> {
	
	Optional<RoleModel> findByName(RoleType name);

}
