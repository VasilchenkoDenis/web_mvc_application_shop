package com.denisvasilchenko.web_mvc_application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min=2, message = "Not less then 2 symbols")
    @NotNull
    private String name;
    @Size(min=2, message = "Not less than 2 symbols")
    private String surname;
    private String password;
    private String role;

    public User() {
    }

    public User(String name, String surname, String role, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @Size(min = 2, message = "Not less then 2 symbols") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, message = "Not less then 2 symbols") String name) {
        this.name = name;
    }

    public @Size(min = 2, message = "Not less than 2 symbols") String getSurname() {
        return surname;
    }

    public void setSurname(@Size(min = 2, message = "Not less than 2 symbols") String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
