package com.example.dynamicQ.vo;

public class LogInReq {

	private String email;

	private String password;

	public LogInReq() {
		super();
	}

	public LogInReq(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
