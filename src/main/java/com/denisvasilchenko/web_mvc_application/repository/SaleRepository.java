package com.denisvasilchenko.web_mvc_application.repository;


import com.denisvasilchenko.web_mvc_application.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
