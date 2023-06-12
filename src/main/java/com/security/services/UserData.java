package com.security.services;

import com.security.payload.request.LoginRequest;
import com.security.payload.request.SignupRequest;
import com.security.payload.request.UserRequest;
import com.security.response.Response;

public interface UserData {

	public Response addUser(SignupRequest signupForm);
	
	public Response modifyUser(UserRequest userRequest);
	
	public Response signin(LoginRequest loginRequest);
}
