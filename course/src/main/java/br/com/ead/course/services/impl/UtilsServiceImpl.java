package br.com.ead.course.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UtilsServiceImpl{

	   public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
	        return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
	                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
	    }
}
