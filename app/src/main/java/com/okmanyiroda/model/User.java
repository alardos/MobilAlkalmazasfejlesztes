package com.okmanyiroda.model;


import java.util.HashMap;
import java.util.Locale;

public class User {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String personalid;
	
	public User(){}
	
	public User(String firstName, String lastName, String email, String password, String personalID){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.personalid = personalID;
	}
	
	public HashMap<String, String> asHash(){
		HashMap<String, String> userHash = new HashMap<String, String>();
		userHash.put("id",this.getId());
		userHash.put("personalId",this.getPersonalid());
		userHash.put("email", this.getEmail());
		userHash.put("firstName",this.getFirstname());
		userHash.put("lastName",this.getLastname());
		userHash.put("password",this.getPassword());
		return userHash;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstName;
	}
	
	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastname() {
		return lastName;
	}
	
	public void setLastname(String lastName) {
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
	
	public String getPersonalid() {
		return personalid.toLowerCase(Locale.ROOT);
	}
	
	public void setPersonalid(String personalid) {
		this.personalid = personalid.toLowerCase(Locale.ROOT);
	}
}
