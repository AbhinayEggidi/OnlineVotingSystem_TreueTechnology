package com.example.voting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	 
    private String username;
    private String password;
    private String voting;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Long id, String username, String password,String vote) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.voting=voting;
		 
	}
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getVote() {
		return voting;
	}
	public void setVote(String voting) {
		this.voting = voting;
	}
	
    
}
