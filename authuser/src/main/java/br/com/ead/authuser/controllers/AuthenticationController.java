package br.com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ead.authuser.dto.UserDto;
import br.com.ead.authuser.dto.UserDto.UserView;
import br.com.ead.authuser.services.UserService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {
	
	

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Object> create(@RequestBody @Validated(UserView.UserPost.class) @JsonView(UserView.UserPost.class) UserDto userDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));

	}

}
