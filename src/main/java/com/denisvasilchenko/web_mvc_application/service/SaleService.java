package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.repository.ProductRepository;
import com.denisvasilchenko.web_mvc_application.repository.SaleRepository;
import com.denisvasilchenko.web_mvc_application.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Shop shop;

    public void addSale(List<Product> products, User user) throws Exception {
        saleRepository.save(shop.createSale(products, user));
    }
}
