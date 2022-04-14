package br.com.ead.course.specification;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
import br.com.ead.course.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {
	
	
	@And({ @Spec(path = "name", spec = Like.class), 
		   @Spec(path = "status", spec = Equal.class),
		   @Spec(path = "level", spec = Equal.class) 
	})
	public interface CourseSpec extends Specification<CourseModel>{}
	
	
	@Spec(path = "title", spec = Like.class)
	public interface ModuleSpec extends Specification<ModuleModel> {
	}
	
	@Spec(path = "title", spec = Like.class)
	public interface LessonSpec extends Specification<LessonModel> {
	}
	
	 @And({
	     @Spec(path="email", spec= Like.class),
	     @Spec(path = "fullName", spec = Like.class),
	     @Spec(path = "userName", spec = Like.class),
	     @Spec(path="status", spec= Equal.class),
	     @Spec(path="type", spec= Equal.class)})
	 public interface UserSpec extends Specification<UserModel> {}
	
	public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
		return (root, query, cb) -> {
			query.distinct(true);
			Root<ModuleModel> module = root;
			Root<CourseModel> course = query.from(CourseModel.class);
			Expression<Collection<ModuleModel>> courseModules = course.get("modules");
			return cb.and(cb.equal(course.get("id"), courseId), cb.isMember(module, courseModules));
		};
	}
	
//	public static Specification<CourseModel> courseUserId(final UUID userId) {
//		return (root, query, cb) -> {
//			query.distinct(true);
//			Join<CourseModel,CourseUserModel> courseUsers = root.join("users");
//			return cb.equal(courseUsers.get("userId"), userId);
//		};
//	}
	
	  public static Specification<UserModel> userCourseId(final UUID courseId) {
	        return (root, query, cb) -> {
	            query.distinct(true);
	            Root<UserModel> user = root;
	            Root<CourseModel> course = query.from(CourseModel.class);
	            Expression<Collection<UserModel>> coursesUsers = course.get("users");
	            return cb.and(cb.equal(course.get("id"), courseId), cb.isMember(user, coursesUsers));
	        };
	    }

	    public static Specification<CourseModel> courseUserId(final UUID userId) {
	        return (root, query, cb) -> {
	            query.distinct(true);
	            Root<CourseModel> course = root;
	            Root<UserModel> user = query.from(UserModel.class);
	            Expression<Collection<CourseModel>> usersCourses = user.get("courses");
	            return cb.and(cb.equal(user.get("id"), userId), cb.isMember(course, usersCourses));
	        };
	    }


}
