package com.denisvasilchenko.web_mvc_application.shop;

import com.denisvasilchenko.web_mvc_application.entity.*;
import com.denisvasilchenko.web_mvc_application.repository.ProductQuantityRepository;
import com.denisvasilchenko.web_mvc_application.repository.ProductRepository;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Shop {

    private boolean isOpen = true;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductQuantityRepository productQuantityRepository;
    @Autowired
    private ProductService productService;

    public Sale createSale(List<Product> products, User user) {
        List<ProductQuantity> productQuantities = new ArrayList<>();
        for (Product product : products) {
            Product foundProduct = productRepository.findById(product.getId()).orElse(null);
            if (foundProduct != null) {
                foundProduct.setQuantity(foundProduct.getQuantity() - product.getQuantity());
                ProductQuantity productQuantity = new ProductQuantity(product, product.getQuantity());
                productQuantityRepository.save(productQuantity);
                productQuantities.add(productQuantity);
                if (foundProduct.getQuantity() <= 0) {
                    foundProduct.setAvailable(false);
                }
                productRepository.save(foundProduct);
            } else {
                throw new RuntimeException("Product not found");
            }
        }
        return new Sale(LocalDateTime.now(), user, productQuantities);
    }

    public Return createReturn(Sale sale, User user) {
        List<ProductQuantity> productQuantities = sale.getProducts();
        for (ProductQuantity pq : productQuantities) {
            Product foundProduct = productService.findProductById(pq.getProduct().getId());
            foundProduct.setQuantity(foundProduct.getQuantity() + pq.getQuantity());
            productService.saveProduct(foundProduct);
        }
        return new Return(LocalDateTime.now(), user, sale);
    }
}
