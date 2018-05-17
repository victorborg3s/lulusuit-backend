package com.lulusuit.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lulusuit.backend.comm.RestData;
import com.lulusuit.backend.comm.RestResponse;

@RestController
@CrossOrigin(origins= {"http://localhost:4200", "http://192.168.1.12:4200"}, maxAge=3600)
public class AuthController {

	@PostMapping("/api/auth/login")
	@ResponseBody
	public RestResponse login( /*@RequestParam(value="username") String username, 
					  @RequestParam(value="password") String password */) {
		System.out.println("blah");
		
		RestResponse response = new RestResponse(true, new RestData("token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTQ5MTUyMjg2NH0.OZQPWEgs-JaABOCEodulSiN-yd-T1gmZzrswY4kaNKNI_FyOVFJPaBsAqkcD3SgN010Y4VSFSNh6DXNqHwNMIw"));
		response.getData().getMessages().add("Mensagem 1...");
		response.getData().getMessages().add("Mensagem 2...");
		response.getData().getErrors().add("Erro 1...");
		
		return response;
	}

	@DeleteMapping("/api/auth/logout")
	@ResponseBody
	public RestResponse logout() {
		
		return new RestResponse(true, new RestData(""));
	}

}
