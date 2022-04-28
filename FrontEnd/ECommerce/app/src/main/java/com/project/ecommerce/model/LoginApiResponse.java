package com.project.ecommerce.model;

public class LoginApiResponse {

    private int id;
    private String name;
    private String email;
    private final boolean error;
    private final String message;
    private String password;
    private boolean isAdmin;

    public LoginApiResponse(String message) {
        this.message = message;
        this.error = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }


    public boolean isAdmin() {
        return isAdmin;
    }
}
