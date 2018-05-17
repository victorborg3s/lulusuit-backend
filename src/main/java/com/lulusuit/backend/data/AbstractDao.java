package com.lulusuit.backend.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AbstractDao<T extends Serializable>  implements IGenericDao<T> {

	private Class<T> clazz;

	@PersistenceContext
	@Autowired
	protected EntityManager entityManager;

	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T getById(Long id) {
		return entityManager.find(clazz, id);
	}

	public List<T> listAll() {
		return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
	}

	@Transactional
	public void create(T entity) {
		entityManager.persist(entity);
	}

	@Transactional
	public T update(T entity) {
		return entityManager.merge(entity);
	}

	@Transactional
	public void delete(T entity) {
		entityManager.remove(entity);
	}

	@Transactional
	public void deleteById(Long entityId) {
		T entity = getById(entityId);
		delete(entity);
	}

}
