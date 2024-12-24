package com.denisvasilchenko.web_mvc_application.entity;


import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="role")
    private Roles role;

    public Role(String roleUser) {
        this.role=Roles.valueOf(roleUser);
    }
    public Role() {}

    @Override
    public String toString() {
        return role.name();
    }
}
