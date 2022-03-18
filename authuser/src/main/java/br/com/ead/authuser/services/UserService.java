package br.com.ead.authuser.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.ead.authuser.dto.UserDto;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.specifications.SpecificationTemplate.UserSpec;

public interface UserService {

	List<UserModel> findAll();

	UserModel findOne(UUID id);

	void delete(UUID id);

	UserModel update(UUID id, UserDto userDto);

	void updatePassword(UUID id, UserDto userDto);

	UserModel updateImage(UUID id, UserDto userDto);

	UserModel save(UserDto userDto);


	Page<UserModel> findAll(Pageable pageable, Specification<UserModel> specification);

	UserModel subscriptionInstructor(UUID userId);

}
