package com.denisvasilchenko.web_mvc_application.repository;

import com.denisvasilchenko.web_mvc_application.entity.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
}
