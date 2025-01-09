package com.denisvasilchenko.web_mvc_application.repository;

import com.denisvasilchenko.web_mvc_application.entity.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByName(@Size(min = 2, message = "Not less then 2 symbols") String name);


}
