package com.expense.dto;

import jakarta.validation.constraints.Pattern;

public class RegisterRequest {

    private String name;

    private String username;

    
    @Pattern(
    	    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
    	    message = "Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number and one special character"
    	)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}