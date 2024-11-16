package com.intellect.serverstatuschecker.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class LoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String loginUserName;
	
	private String loginPassword;
	
	private String authToken;

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(loginPassword, loginUserName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginDTO other = (LoginDTO) obj;
		return Objects.equals(loginPassword, other.loginPassword) && Objects.equals(loginUserName, other.loginUserName);
	}

	@Override
	public String toString() {
		return "LoginDTO [loginUserName=" + loginUserName + ", loginPassword=" + loginPassword + ", authToken="
				+ authToken + "]";
	}

}
