package br.com.ead.course.dto;

import java.io.Serializable;
import java.util.UUID;

import br.com.ead.course.enums.UserStatus;
import br.com.ead.course.enums.UserType;
import lombok.Data;

@Data
public class UserDto implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String userName;
	private String email;
	private String fullName;
	private UserStatus status;
	private UserType type;
	private String phoneNumber;
	private String cpf;
	private String imageURL;
}
