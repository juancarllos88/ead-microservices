package br.com.ead.course.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T> {

	T save(Object dto);

	T findOne(UUID id);

	void delete(UUID id);
	
	T update(UUID id, Object dto);

	Page<T> findAll(Pageable pageable);

	Page<T> findAll(Pageable pageable, Specification<T> specification);

}
