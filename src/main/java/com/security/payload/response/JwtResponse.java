package com.security.payload.response;

import java.util.List;
import java.util.Map;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String userIdentity;
	private String email;
	private String status;
	private List<String> roles;
	private Map<String,String> activities;

	
	

	public JwtResponse(String token, String type, Long id, String username, String userIdentity, String email,
			 Map<String, String> activities,String status,List<String> roles) {
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.userIdentity = userIdentity;
		this.email = email;
		this.roles = roles;
		this.status = status;
		this.activities = activities;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public Map<String, String> getActivities() {
		return activities;
	}

	public void setActivities(Map<String, String> activities) {
		this.activities = activities;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
