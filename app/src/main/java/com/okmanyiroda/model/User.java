package com.okmanyiroda.model;


import java.util.HashMap;

public class User {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String personalID;
	
	public User(String firstName, String lastName, String email, String password, String personalID){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.personalID = personalID;
	}
	
	public HashMap<String, String> asHash(){
		HashMap<String, String> userHash = new HashMap<String, String>();
		userHash.put("id",this.getId());
		userHash.put("personalId",this.getPersonalId());
		userHash.put("email", this.getEmail());
		userHash.put("firstName",this.getFirstName());
		userHash.put("lastName",this.getLastName());
		userHash.put("password",this.getPassword());
		return userHash;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public String getPersonalId() {
		return personalID;
	}
	
	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}
}
