package br.com.ead.authuser.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ead.authuser.dto.UserDto;
import br.com.ead.authuser.dto.UserDto.UserView;
import br.com.ead.authuser.dto.events.UserEventDto;
import br.com.ead.authuser.enums.ActionType;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.publishers.UserEventPublisher;
import br.com.ead.authuser.services.UserService;
import br.com.ead.authuser.specifications.SpecificationTemplate;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserEventPublisher UserEventPublisher;
	
	@GetMapping("/mq")
	public ResponseEntity<String> message() {
		var dto = new UserEventDto();
		UserEventPublisher.publishUserEvent(dto, ActionType.CREATE);
		return ResponseEntity.status(HttpStatus.OK).body("Mensagem Enviada");
	}

	@GetMapping
	public ResponseEntity<Page<UserModel>> findAll(SpecificationTemplate.UserSpec specification, 
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<UserModel> userModelPage = userService.findAll(pageable, specification);
		return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserModel> findOne(@PathVariable UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		userService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body("");
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserModel> update(@PathVariable UUID id,
			@RequestBody @Validated(UserView.UserPut.class) @JsonView(UserView.UserPut.class) UserDto userDto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userDto));
	}

	@PatchMapping("/{id}/password")
	public ResponseEntity<String> updatePassword(@PathVariable UUID id,
			@RequestBody @Validated(UserView.PasswordPatch.class) @JsonView(UserView.PasswordPatch.class) UserDto userDto) {
		userService.updatePassword(id, userDto);
		return ResponseEntity.status(HttpStatus.OK).body("Password updated sucessfully");
	}

	@PatchMapping("/{id}/image")
	public ResponseEntity<UserModel> updateImage(@PathVariable UUID id,
			@RequestBody @Validated(UserView.ImagePatch.class) @JsonView(UserView.ImagePatch.class) UserDto userDto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateImage(id, userDto));
	}
	
	@PatchMapping("/{id}/instructors/subscription")
	public ResponseEntity<UserModel> subscriptionInstructor(@PathVariable UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.subscriptionInstructor(id));
	}

}
