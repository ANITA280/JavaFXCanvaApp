package model;

import java.io.FileInputStream;
import java.io.InputStream;

//user class to store all the information of the users on the program and display on the profile 
public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String logo;

	public User() {
	}

	public User(String username, String password, String firstName, String lastName, String logo) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.logo = logo;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getlogo() {
		return logo;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public void setlogo(String logo) {
		this.logo = logo;
	}

}
