package com.security.controllers;

//import
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.payload.request.LoginRequest;
import com.security.payload.request.SignupRequest;
import com.security.payload.request.UserRequest;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;
import com.security.response.Response;
import com.security.secure.jwt.JwtUtils;
import com.security.services.UserData;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserData userData;

	@RequestMapping(value="/signin",method=RequestMethod.POST)
	public Response authenticateUser(@RequestBody LoginRequest loginRequest) {

		Response res = null;
		res = userData.signin(loginRequest);
		return res;
	}

	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public Response registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		Response res = null;
		res = userData.addUser(signUpRequest);
		return res;
	}
	
	@RequestMapping(value="/modify-user",method=RequestMethod.POST)
	public Response modifyUser(@Valid @RequestBody UserRequest userRequest) {
		Response res = null;
		res = userData.modifyUser(userRequest);
		return res;
	}
}