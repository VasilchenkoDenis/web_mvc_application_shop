package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.ProductQuantity;
import com.denisvasilchenko.web_mvc_application.repository.ProductQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductQuantityService {
    @Autowired
    private ProductQuantityRepository productQuantityRepository;

    public void save(ProductQuantity productQuantity) {
        productQuantityRepository.save(productQuantity);
    }
}
