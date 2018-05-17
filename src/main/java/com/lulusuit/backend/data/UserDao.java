package com.lulusuit.backend.data;

import com.lulusuit.backend.entity.User;

public interface UserDao extends IGenericDao<User> {
	 
    User findByUsername(String username);
    
}