package com.intellect.serverstatuschecker.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class LogInDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String userRole;
	
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, userName, userRole);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogInDataDTO other = (LogInDataDTO) obj;
		return Objects.equals(token, other.token) && Objects.equals(userName, other.userName)
				&& Objects.equals(userRole, other.userRole);
	}

	@Override
	public String toString() {
		return "LogInDataDTO [userName=" + userName + ", userRole=" + userRole + ", token=" + token + "]";
	}
	
}
