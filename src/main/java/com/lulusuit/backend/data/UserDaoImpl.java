package com.lulusuit.backend.data;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lulusuit.backend.entity.User;

@Repository
public class UserDaoImpl extends GenericDao<User> implements UserDao {

	public UserDaoImpl() {
		this.setClazz(User.class);
	}
	
	@Override
	public User findByUsername(String username) {
		User u = null;
		if (entityManager.isOpen()) {
			TypedQuery<User> q = entityManager.createNamedQuery("findByUsername", User.class);
			q.setParameter("username", username);
			u = q.getSingleResult();
		}
		return u;
	}

}
