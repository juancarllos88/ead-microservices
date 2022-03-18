package br.com.ead.authuser.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.ead.authuser.annotation.UserNameConstraint;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public interface UserView {
		public static interface UserPost {
		}

		public static interface UserPut {
		}

		public static interface PasswordPatch {
		}

		public static interface ImagePatch {
		}
	}

	private UUID userId;

	@NotBlank(groups = UserView.UserPost.class)
	@Size(min = 4, max = 50, groups = UserView.UserPost.class)
	@JsonView(UserView.UserPost.class)
	@UserNameConstraint(groups = UserView.UserPost.class)
	private String userName;

	@NotBlank(groups = UserView.UserPost.class)
	@Email(groups = UserView.UserPost.class)
	@JsonView(UserView.UserPost.class)
	private String email;

	@NotBlank(groups = { UserView.UserPost.class, UserView.PasswordPatch.class })
	@Size(min = 4, max = 20, groups = { UserView.UserPost.class, UserView.PasswordPatch.class })
	@JsonView({ UserView.UserPost.class, UserView.PasswordPatch.class })
	private String password;

	@NotBlank(groups = UserView.PasswordPatch.class)
	@Size(min = 4, max = 20, groups = UserView.PasswordPatch.class)
	@JsonView(UserView.PasswordPatch.class)
	private String oldPassword;

	@JsonView({ UserView.UserPost.class, UserView.UserPut.class })
	private String fullName;

	@JsonView({ UserView.UserPost.class, UserView.UserPut.class })
	private String phoneNumber;

	@JsonView({ UserView.UserPost.class, UserView.UserPut.class })
	private String cpf;

	@NotBlank(groups = UserView.ImagePatch.class)
	@JsonView(UserView.ImagePatch.class)
	private String imageURL;
	

}
