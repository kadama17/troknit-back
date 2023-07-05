package com.example.troknite.troknite.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UsersDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String surname;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
