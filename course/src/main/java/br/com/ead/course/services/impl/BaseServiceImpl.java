package br.com.ead.course.services.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ead.course.exceptions.EntityModelNotFoundException;
import br.com.ead.course.repositories.BaseRepository;
import br.com.ead.course.services.BaseService;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	@Override
	@Transactional
	public T save(Object dto) {
		T entityModel = getEntityModel();
		BeanUtils.copyProperties(dto, entityModel);
		return getRepository().save(entityModel);
	}

	@Override
	@Transactional
	public T findOne(UUID id) {
		return getRepository().findById(id).orElseThrow(() -> new EntityModelNotFoundException());
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		T entityModel = findOne(id);
		getRepository().delete(entityModel);

	}

	@Override
	@Transactional
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	@Override
	@Transactional
	public T update(UUID id, Object dto) {
		T entityModel = findOne(id);
		BeanUtils.copyProperties(dto, entityModel);
		return getRepository().save(entityModel);
	}

	protected abstract BaseRepository<T, UUID> getRepository();

	protected abstract T getEntityModel();

}
