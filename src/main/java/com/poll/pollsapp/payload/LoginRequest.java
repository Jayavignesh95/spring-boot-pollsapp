package com.poll.pollsapp.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank
	private String userNameorEMail;
	
	@NotBlank
	private String password;

	public String getUserNameorEMail() {
		return userNameorEMail;
	}

	public void setUserNameorEMail(String userNameorEMail) {
		this.userNameorEMail = userNameorEMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
