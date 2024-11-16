package com.intellect.serverstatuschecker.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class UsersDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String userSurname;
	
	private String userFirstName;
	
	private String userLastName;
	
	private String userName;
	
	private String password;
	
	private String userRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, password, userFirstName, userLastName, userName, userRole, userSurname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersDTO other = (UsersDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(userFirstName, other.userFirstName)
				&& Objects.equals(userLastName, other.userLastName) && Objects.equals(userName, other.userName)
				&& Objects.equals(userRole, other.userRole) && Objects.equals(userSurname, other.userSurname);
	}

	@Override
	public String toString() {
		return "UsersDTO [id=" + id + ", userSurname=" + userSurname + ", userFirstName=" + userFirstName
				+ ", userLastName=" + userLastName + ", userName=" + userName + ", password=" + password + ", userRole="
				+ userRole + "]";
	}
	
}
