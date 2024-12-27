package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.List;
import java.util.Optional;

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
    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product findProductByName(String name) {
        Product product = productRepository.findByName(name);
        if (product!=null) {return product;}
        List<Product> products = productRepository.findAll();
        Product suitableProduct = null;
        int minDistance = Integer.MAX_VALUE;
        for(Product p : products) {
            String str = p.getName();
            LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
            int distance = levenshteinDistance.apply(str, name);
            System.out.println(distance);
            if (distance < minDistance) {
                minDistance = distance;
                suitableProduct=p;}
        }

        return suitableProduct;
    }
}
