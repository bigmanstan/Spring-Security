package com.security.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activities")
public class Activity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String roleName;
	
	private String roleAct;
	
	private String roleUrl;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleAct() {
		return roleAct;
	}

	public void setRoleAct(String roleAct) {
		this.roleAct = roleAct;
	}

	public String getRoleUrl() {
		return roleUrl;
	}

	public void setRoleUrl(String roleUrl) {
		this.roleUrl = roleUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
