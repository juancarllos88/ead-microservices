package br.com.ead.authuser.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.authuser.enums.RoleType;
import br.com.ead.authuser.models.RoleModel;
import br.com.ead.authuser.repositories.RoleRepository;
import br.com.ead.authuser.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<RoleModel> findByName(RoleType name) {
		return roleRepository.findByName(name);
	}

}
