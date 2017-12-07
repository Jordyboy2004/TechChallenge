package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    public User(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email    = email;
    }
    
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    public String password;
	public String username;
    public String email;
    
    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
    	return email;
    }
    public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    User() { // jpa only
    }


}
