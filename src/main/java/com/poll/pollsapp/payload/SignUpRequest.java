package com.poll.pollsapp.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

	
	@NotBlank
	@Size(min=4,max=40)
	private String name;
	
	@NotBlank
	@Size(min=4,max=15)
	private String username;

	@NotBlank
	@Email
	@Size(max=45)
	private String email;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotBlank
	@Size(min=8,max=16)
	private String password;
}
