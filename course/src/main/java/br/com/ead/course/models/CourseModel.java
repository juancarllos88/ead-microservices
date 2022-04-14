package br.com.ead.course.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ead.course.enums.CourseLevel;
import br.com.ead.course.enums.CourseStatus;
import br.com.ead.course.listener.CourseModelListener;
import lombok.Data;

@EntityListeners(CourseModelListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
@Data
public class CourseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false, length = 250)
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseStatus status;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseLevel level;

	@Column
	private String imageURL;

	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime creationDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Column(nullable = false)
	private LocalDateTime lastUpdateDate;
	
	@Column(nullable = false)
	private UUID userInstructor;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY /* , cascade = CascadeType.ALL, orphanRemoval = true */ )
	@Fetch(FetchMode.SUBSELECT)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ModuleModel> modules;
	
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
//	//@OnDelete(action = OnDeleteAction.CASCADE)
//	private Set<CourseUserModel> users;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_COURSES_USERS",
               joinColumns = @JoinColumn(name = "course_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserModel> users;

}
