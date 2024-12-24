package com.denisvasilchenko.web_mvc_application.repository;


import com.denisvasilchenko.web_mvc_application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
