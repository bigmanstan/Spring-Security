package com.security.secure.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.exception.CustomException;
import com.security.models.Activity;
import com.security.models.ERole;
import com.security.models.Role;
import com.security.models.User;
import com.security.payload.request.LoginRequest;
import com.security.payload.request.SignupRequest;
import com.security.payload.request.UserRequest;
import com.security.payload.response.JwtResponse;
import com.security.repository.ActivityRepository;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;
import com.security.response.ErrorResponse;
import com.security.response.Response;
import com.security.secure.jwt.JwtUtils;
import com.security.services.UserData;

/**
 * @author Ishmeet
 *
 */
@Service
public class UserDataImpl implements UserData {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public Response addUser(SignupRequest signupForm) {
		Response res = null;
		try {
			if (signupForm.getEmail().isEmpty() || signupForm.getUsername().isEmpty()
					|| signupForm.getPassword().isEmpty()) {
				throw new CustomException("Not Valid data", null);
			}

			if (userRepository.existsByUsername(signupForm.getUsername())) {
				throw new CustomException("Username already exits", null);
			} else if (userRepository.existsByEmail(signupForm.getEmail())) {
				throw new CustomException("Email already exits", null);
			} else {
				User user = new User(signupForm.getUsername(), signupForm.getEmail(),
						encoder.encode(signupForm.getPassword()),signupForm.getUserIdentity(),"ACTIVE");

				Set<String> strRoles = signupForm.getRole();
				Set<Role> roles = new HashSet<>();

				if (strRoles == null) {
					Role userRole = roleRepository.findByName(ERole.ROLE_USER);
					roles.add(userRole);
				} else {
					strRoles.forEach(role -> {
						switch (role) {
						case "admin":
							Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
							roles.add(adminRole);

							break;
						case "mod":
							Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR);

							roles.add(modRole);

							break;
						default:
							Role userRole = roleRepository.findByName(ERole.ROLE_USER);
							roles.add(userRole);
						}
					});
				}
				Role userRole = roleRepository.findByName(ERole.ROLE_USER);
				roles.add(userRole);
				user.setRoles(roles);
				userRepository.save(user);
				res = new Response();
				res.setCode("200");
				res.setMessage("User registered successfully!");
				res.setData(user);
			}
		} catch (CustomException exception) {
			res = new ErrorResponse();
			res.setCode("400");
			res.setData(null);
			res.setMessage(exception.getMessage());
		}
		return res;
	}

	@Override
	public Response signin(LoginRequest loginRequest) {
		Response res = null;
		List<String> actiList = new ArrayList<>();
		Map<String, String> doubleBraceMap  = new HashMap<String, String>();
		try {
			if (loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()
					|| loginRequest.getUsername() == "" || loginRequest.getPassword() == "") {
				throw new CustomException("Empty data", null);
			} else {
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
								loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				if(jwt=="INACTIVE") {
					throw new CustomException("INACTIVE User", null);
				}
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
				List<Activity> act = (List<Activity>) activityRepository.findByRoleName(userDetails.getUserIdentity());
				for(Activity a: act) {
					actiList.add(a.getRoleAct());
					doubleBraceMap.put(a.getRoleAct(), a.getRoleUrl());
				}
				res = new Response();
				
				res.setCode("200");
				
				res.setData(new JwtResponse(jwt, "Bearer", userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),userDetails.getUserIdentity(),doubleBraceMap,
						userDetails.getStatus(),roles));
				res.setMessage("Logged in");
			}

		} catch (CustomException exception) {
			res = new ErrorResponse();
			res.setCode("400");
			res.setData(null);
			res.setMessage(exception.getMessage());
		}
		return res;
	}

	@Override
	public Response modifyUser(UserRequest userRequest) {
		Response res = null;
		try {
			User user = userRepository.findByUsername(userRequest.getUsername());
			if(user==null) {
				throw new CustomException("", null);
			} else {
				user.setEmail(userRequest.getEmail());
				user.setStatus(userRequest.getStatus());
				user.setUserIdentity(userRequest.getUserIdentity());
				userRepository.save(user);
				res = new Response();
				res.setCode("200");
				res.setData(user);
				res.setMessage("Modified");
			}
		}catch(CustomException e) {
			res = new ErrorResponse();
			res.setCode("400");
			res.setMessage(e.getMessage());
		}
		return res;
	}

}
