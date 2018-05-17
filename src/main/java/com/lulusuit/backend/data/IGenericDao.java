package com.lulusuit.backend.data;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable> {

	T getById(final Long id);

	List<T> listAll();

	void create(final T entity);

	T update(final T entity);

	void delete(T entity);
	
	void deleteById(final Long entityId);

}
