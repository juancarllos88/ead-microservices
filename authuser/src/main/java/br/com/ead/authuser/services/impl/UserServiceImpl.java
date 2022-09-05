package br.com.ead.authuser.services.impl;

import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ead.authuser.clients.CourseClient;
import br.com.ead.authuser.configuration.security.AuthenticationCurrentUserService;
import br.com.ead.authuser.configuration.security.JwtProvider;
import br.com.ead.authuser.configuration.security.UserDetailsImpl;
import br.com.ead.authuser.controllers.UserController;
import br.com.ead.authuser.dto.LoginDto;
import br.com.ead.authuser.dto.UserDto;
import br.com.ead.authuser.dto.events.UserEventDto;
import br.com.ead.authuser.enums.ActionType;
import br.com.ead.authuser.enums.RoleType;
import br.com.ead.authuser.enums.UserStatus;
import br.com.ead.authuser.enums.UserType;
import br.com.ead.authuser.exceptions.EmailExistsException;
import br.com.ead.authuser.exceptions.EntityModelNotFoundException;
import br.com.ead.authuser.exceptions.MismatchedPasswordException;
import br.com.ead.authuser.exceptions.UserNameExistsException;
import br.com.ead.authuser.models.RoleModel;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.publishers.UserEventPublisher;
import br.com.ead.authuser.repositories.UserRepository;
import br.com.ead.authuser.services.ConverterService;
import br.com.ead.authuser.services.RoleService;
import br.com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
	
	private static Logger logg = LoggerFactory.getLogger(CourseClient.class);

	@Autowired
	private UserEventPublisher userEventPublisher;
	
	@Autowired
	private ConverterService converterService;
	
	@Autowired
	private CourseClient courseClient;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationCurrentUserService authenticationCurrentUserService;
	
	private final UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public List<UserModel> findAll() {
		return repository.findAll();

	}
	
	@Override
	@Transactional
	//@Cacheable(cacheNames = "USer", key="#id")
	public UserModel findById(UUID id) {
		UserDetailsImpl currentUser = authenticationCurrentUserService.getCurrentUser();
		if(!id.equals(currentUser.getId())) {
			throw new AccessDeniedException("Forbidden");
		}
		return repository.findById(id).orElseThrow(() -> new EntityModelNotFoundException());

	}

	@Override
	@Transactional
	public UserModel findOne(UUID id) {
		return repository.findById(id).orElseThrow(() -> new EntityModelNotFoundException());

	}

	@Override
	@Transactional
	//@CacheEvict(cacheNames = "User", key="#id	")
	public void delete(UUID id) {
		UserModel user = findOne(id);
//		Optional<List<UserCourseModel>> userCourseModel = userCourseRepository.findByUser(user);
//		if(userCourseModel.isPresent()) {
//			userCourseRepository.deleteAll(userCourseModel.get());
//		}
		repository.delete(user);
		userEventPublisher.publishUserEvent(converterService.convert(user, UserEventDto.class), ActionType.DELETE);
		courseClient.deleteUserInCourse(id);

	}

	@Override
	@Transactional
	//@CacheEvict(cacheNames = "User", allEntries = true)
	public UserModel save(UserDto userDto) {
		log.debug("UserDto from request {}", userDto.toString());
		fieldsValidation(userDto.getUserName(), userDto.getEmail());
		RoleModel roleModel = roleService.findByName(RoleType.ROLE_STUDENT).orElseThrow(() -> new RuntimeException("Role is not found"));
		var userModel = new UserModel();
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		BeanUtils.copyProperties(userDto, userModel);
		userModel.setStatus(UserStatus.ACTIVE);
		userModel.setType(UserType.STUDENT);
		userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userModel.getRoles().add(roleModel);
		UserModel userModelSaved = repository.save(userModel);
		log.info("UserModel saved with id {}", userModelSaved.getId());
		userEventPublisher.publishUserEvent(converterService.convert(userModelSaved, UserEventDto.class), ActionType.CREATE);
		return userModelSaved;
	}

	@Transactional()
	private void fieldsValidation(String userName, String email) {
		Optional<UserModel> user = repository.findByUserName(userName);
		if (user.isPresent()) {
			log.warn("Username {} already exists", userName);
			throw new UserNameExistsException();
		}
		user = repository.findByEmail(email);
		if (user.isPresent()) {
			log.warn("Email {} already exists", userName);
			throw new EmailExistsException();
		}
	}

	@Override
	@Transactional
	//@CachePut(cacheNames = "User", key="#id")
	public UserModel update(UUID id, UserDto userDto) {
		var user = findOne(id);
		user.setFullName(Objects.nonNull(userDto.getFullName()) ? userDto.getFullName() : user.getFullName());
		user.setPhoneNumber(Objects.nonNull(userDto.getPhoneNumber()) ? userDto.getPhoneNumber() : user.getPhoneNumber());
		user.setCpf(Objects.nonNull(userDto.getCpf()) ? userDto.getCpf() : user.getCpf());
		user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		user = repository.save(user);
		userEventPublisher.publishUserEvent(converterService.convert(user, UserEventDto.class), ActionType.UPDATE);
		return user;
	}

	@Override
	public void updatePassword(UUID id, UserDto userDto) {
		var user = findOne(id);
		if(!user.getPassword().equals(userDto.getOldPassword())) {
			throw new MismatchedPasswordException();
		}
		user.setPassword(userDto.getPassword());
		user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		repository.save(user);

	}

	@Override
	public UserModel updateImage(UUID id, UserDto userDto) {
		var user = findOne(id);
		user.setImageURL(userDto.getImageURL());
		user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		return repository.save(user);
	}

	@Override
	@Cacheable(cacheNames = "User", key = "#root.method.name")
	public Page<UserModel> findAll(Pageable pageable,Specification<UserModel> specification) {
		Page<UserModel> users = repository.findAll(specification,pageable);
		if(!users.isEmpty()) {
			for (UserModel user : users) {
				user.add(linkTo(methodOn(UserController.class).find(user.getId())).withSelfRel());
			}
		}
		return users;
	}

	@Transactional
	@Override
	public UserModel subscriptionInstructor(UUID userId) {
		UserModel userModel = findOne(userId);
		RoleModel roleModel = roleService.findByName(RoleType.ROLE_INSTRUCTOR)
				.orElseThrow(() -> new RuntimeException("Role is not found"));
		userModel.setType(UserType.INSTRUCTOR);
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userModel.getRoles().add(roleModel);
		userModel = repository.save(userModel);
		userEventPublisher.publishUserEvent(converterService.convert(userModel, UserEventDto.class), ActionType.UPDATE);
		return userModel;
	}

	@Override
	public String authenticate(LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwt(authentication);
		log.info("Login from user {} with sucess",loginDto.getUserName());
		logg.info("Login from user {} with sucess",loginDto.getUserName());
		return jwt;
	}

}
