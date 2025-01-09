package com.denisvasilchenko.web_mvc_application.repository;


import com.denisvasilchenko.web_mvc_application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByNameAndPurchasePrice(String name, Double purchasePrice);

    Product findByName(String name);

    List<Product> findAllByAvailableTrue();
}
