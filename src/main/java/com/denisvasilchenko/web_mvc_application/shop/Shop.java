package com.denisvasilchenko.web_mvc_application.shop;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.Sale;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class Shop {

    @Autowired
    private ProductRepository productRepository;

    public Sale createSale (List<Product> products, User user) {
        for(Product product : products) {
            Product foundProduct = productRepository.findById(product.getId()).orElse(null);
            if (foundProduct != null) {
                foundProduct.setQuantity(foundProduct.getQuantity() - product.getQuantity());
                if(foundProduct.getQuantity() <= 0) {foundProduct.setAvailable(false);}
                productRepository.save(foundProduct);
            }
            else{ throw new RuntimeException("Product not found"); }
        }
        return new Sale(LocalDateTime.now(), user, products );
    }
}
