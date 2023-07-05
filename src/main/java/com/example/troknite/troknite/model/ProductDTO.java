package com.example.troknite.troknite.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ProductDTO  implements Serializable{

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 3055)
    private String description;
    
    private String category; 

    private Double value;

    private Boolean available;
    
    private List<MultipartFile> imageFiles;




    private Long users;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(final Boolean available) {
        this.available = available;
    }


    public Long getUsers() {
        return users;
    }

    public void setUsers(final Long users) {
        this.users = users;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<MultipartFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<MultipartFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

}
