package com.ecommerce.model;

public class LoginApiResponse {

    private int id;
    private String name;
    private String email;
    private boolean error;
    private String message;
    private String password;
    private boolean isAdmin;
	@Override
	public String toString() {
		return "LoginApiResponse [id=" + id + ", name=" + name + ", email=" + email + ", error=" + error + ", message="
				+ message + ", password=" + password + ", isAdmin=" + isAdmin + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public LoginApiResponse(int id, String name, String email, boolean error, String message, String password, boolean isAdmin) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.error = error;
		this.message = message;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public LoginApiResponse(boolean error, String message) {
		super();
		this.error = error;
		this.message = message;
	}

    
}
