package br.com.ead.authuser.models;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ead.authuser.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_ROLES")
public class RoleModel implements GrantedAuthority, Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	
	@Column(nullable = false, unique = true, length = 30)
	@Enumerated(EnumType.STRING)
	private RoleType name;
	
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	private Set<UserModel> users;
	
	
	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.name.toString();
	}
	

}
