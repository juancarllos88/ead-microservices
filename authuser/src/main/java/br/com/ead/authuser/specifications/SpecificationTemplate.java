package br.com.ead.authuser.specifications;

import java.util.UUID;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {
	
	@And({ @Spec(path = "type", spec = Equal.class), 
		   @Spec(path = "status", spec = Like.class),
		   @Spec(path = "email", spec = Like.class) 
	})
	public interface UserSpec extends Specification<UserModel> {
	}
	
	
//	public static Specification<UserModel> userCourseId(final UUID courseId) {
//		return (root, query, cb) -> {
//			query.distinct(true);
//			Join<UserModel,UserCourseModel> userCourses = root.join("courses");
//			return cb.equal(userCourses.get("courseId"), courseId);
//		};
//	}

}
