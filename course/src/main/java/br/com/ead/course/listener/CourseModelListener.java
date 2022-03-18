package br.com.ead.course.listener;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.ead.course.models.CourseModel;

public class CourseModelListener {

	@PrePersist
	public void coursePrePersist(CourseModel course) {
		course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));	
	}
	
	@PreUpdate
	public void coursePreUpdate(CourseModel course) {
		course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));	
	}
	
	
}

