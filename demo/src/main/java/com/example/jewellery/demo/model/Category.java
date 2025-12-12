package com.example.jewellery.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "categories")
public class Category {
@Id
private String id;
private String name; // Necklace, Earrings, Bangles, etc.
private String parentId; // for subcategories (nullable)
private String description;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getParentId() {
	return parentId;
}
public void setParentId(String parentId) {
	this.parentId = parentId;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}


}
