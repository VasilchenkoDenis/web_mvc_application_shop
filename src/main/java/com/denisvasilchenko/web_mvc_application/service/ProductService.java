package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public boolean saveProduct(Product product) {
        Product savedProduct = productRepository.findByNameAndPurchasePrice(product.getName(), product.getPurchasePrice());
        if (savedProduct != null) {
            return false;
        }
        productRepository.save(product);
        return true;
    }
}
