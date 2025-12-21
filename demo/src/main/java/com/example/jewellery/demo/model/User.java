package com.example.jewellery.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
@NotBlank(message = "Name is required")
@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
private String name;



public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

@NotBlank
@Pattern(regexp = "^[6-9]\\d{9}$")
private String whatsappNumber;

@Pattern(regexp = "^[6-9]\\d{9}$")
private String alternateNumber;

@NotBlank
private String address;

@Pattern(regexp = "^\\d{6}$")
private String pincode;

@NotBlank
private String state;

@NotBlank
private String district;



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

public String getWhatsappNumber() {
	return whatsappNumber;
}

public void setWhatsappNumber(String whatsappNumber) {
	this.whatsappNumber = whatsappNumber;
}

public String getAlternateNumber() {
	return alternateNumber;
}

public void setAlternateNumber(String alternateNumber) {
	this.alternateNumber = alternateNumber;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getPincode() {
	return pincode;
}

public void setPincode(String pincode) {
	this.pincode = pincode;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getDistrict() {
	return district;
}

public void setDistrict(String district) {
	this.district = district;
}

public LocalDateTime getCreatedAt() {
	return createdAt;
}

}
