package com.denisvasilchenko.web_mvc_application.controller;


import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ResController {
    @Autowired
    private ProductService productService;

    @GetMapping("/store/search")
    public List<Product> search(@RequestParam String searchTerm) {
        return Collections.singletonList(productService.findProductByName(searchTerm));
    }
}
