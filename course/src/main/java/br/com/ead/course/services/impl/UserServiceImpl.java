package br.com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.ead.course.models.UserModel;
import br.com.ead.course.repositories.UserRepository;
import br.com.ead.course.services.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserModel> implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected UserModel getEntityModel() {
		return new UserModel();
	}

	@Override
	public Page<UserModel> findAll(Pageable pageable, Specification<UserModel> specification) {
		return userRepository.findAll(specification, pageable);
	}
	
	@Override
	protected UserRepository getRepository() {
		return userRepository;
	}

}
