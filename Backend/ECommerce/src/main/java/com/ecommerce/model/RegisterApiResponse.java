package com.ecommerce.model;
public class RegisterApiResponse {

    private int id;
    private boolean error;
    private String message;
    private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RegisterApiResponse(int id, boolean error, String message, User user) {
		super();
		this.id = id;
		this.error = error;
		this.message = message;
		this.user = user;
	}
	
	public RegisterApiResponse(boolean error, String message, User user) {
		super();
		this.error = error;
		this.message = message;
		this.user = user;
	}
	
	public RegisterApiResponse(boolean error, String message) {
		super();
		this.error = error;
		this.message = message;
	}
	@Override
	public String toString() {
		return "RegisterApiResponse [id=" + id + ", error=" + error + ", message=" + message + ", user=" + user + "]";
	}

   
}
