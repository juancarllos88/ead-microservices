package br.com.ead.authuser.services;

import java.util.Optional;

import br.com.ead.authuser.enums.RoleType;
import br.com.ead.authuser.models.RoleModel;

public interface RoleService {
	
	
	Optional<RoleModel> findByName(RoleType name);

}
