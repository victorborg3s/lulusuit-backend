package com.lulusuit.backend.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lulusuit.backend.comm.RestData;
import com.lulusuit.backend.comm.RestResponse;
import com.lulusuit.backend.data.UserDao;
import com.lulusuit.backend.entity.Authority;
import com.lulusuit.backend.entity.User;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.12:4200"}, maxAge = 3600)
public class UserController {

	@Autowired
	private UserDao dao;

	@RequestMapping("/api/usuario")
	@ResponseBody
	public Collection<User> getUsers() {
		return dao.listAll();
	}

	@PostMapping("/api/usuario/save")
	@ResponseBody
	public User save(@RequestBody User user) {
		if (user.getId() == null) {
			return dao.update(user);
		} else {
			User u = dao.getById(user.getId());
			u.setAuthority(user.getAuthority());
			u.setEnabled(user.isEnabled());
			u.setId(user.getId());
			if (user.getImageBase64() != null && !user.getImageBase64().isEmpty()) {
				u.setImageBase64(user.getImageBase64());
			}
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				u.setPasswordPlain(user.getPassword());
			}
			u.setName(user.getName());
			u.setUsername(user.getUsername());
			return dao.update(u);
		}
	}

	@RequestMapping("/api/usuario/{id}")
	@ResponseBody
	public User getUserById(@PathVariable("id") long id) {
		return dao.getById(id);
	}

	@DeleteMapping("/api/usuario/delete/{id}")
	@ResponseBody
	public RestResponse deleteUser(@PathVariable("id") long id) {
		User u = dao.getById(id);
		dao.delete(u);
		RestResponse response = new RestResponse(true, new RestData("Usuário '" + u.getUsername() + "' excluído com sucesso!"));
		return response;
	}

	@RequestMapping("/user/admin-insert")
	@ResponseBody
	public RestResponse adminInsert() {
		RestResponse response = null;
		if (dao != null) {
			User user = new User();

			user.setUsername("admin");
			//user.setPassword("$2a$11$m7CJ/TNf3AOaWQJP9cKkbOiW8bAJY3vXmzEn5ISkmu2yjBFE88gj.");
			user.setPasswordPlain("password");
			user.setEnabled(true);
			user.setAuthority(Authority.ROLE_ADMIN);

			dao.create(user);

			response = new RestResponse(true, new RestData(user));
			response.getData().getMessages().add("Usuário 'admin' inserido com sucesso.");
		} else {
			response = new RestResponse(false, new RestData(null));
			response.getData().getMessages().add("Inserção do usuário 'admin' falhou.");

		}

		return response;
	}
}
