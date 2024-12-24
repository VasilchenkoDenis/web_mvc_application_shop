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
    @Column(name="user_name")
    @Size(min=2, message = "Not less then 2 symbols")
    @NotNull
    private String name;
    @Size(min=2, message = "Not less than 2 symbols")
    private String surname;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String surname, Role role) {
        this.name = name;
        this.surname = surname;
        this.roles = new HashSet<>();
        roles.add(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
