package com.example.jewellery.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
@Id
private String id;
private String username;
private String email;
private String password; // store hashed password
private List<String> roles = new ArrayList<>(); // ROLE_USER, ROLE_ADMIN


private LocalDateTime createdAt;


public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}


public String getUsername() {
	return username;
}


public void setUsername(String username) {
	this.username = username;
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


public List<String> getRoles() {
	return roles;
}


public void setRoles(List<String> roles) {
	this.roles = roles;
}


@JsonProperty("createdAtFormatted")
public String getCreatedAtFormatted() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
    return this.createdAt.format(formatter);
}


public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}

}
