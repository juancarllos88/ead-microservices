package br.com.ead.course.models;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ead.course.enums.UserStatus;
import br.com.ead.course.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 150)
	private String fullName;
	
	@Column(nullable = false, length = 150)
	private String userName;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	@Column(length = 20)
	private String cpf;
	
	@Column
	private String imageUrl;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<CourseModel> courses;

}
