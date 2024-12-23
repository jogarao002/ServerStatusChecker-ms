package com.intellect.serverstatuschecker.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class UsersDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String userFirstName;
	
	private String userLastName;
	
	private String email;
	
	private String password;
	
	private String userRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UsersDTO [id=" + id + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", email="
				+ email + ", password=" + password + ", userRole=" + userRole + "]";
	}

}
