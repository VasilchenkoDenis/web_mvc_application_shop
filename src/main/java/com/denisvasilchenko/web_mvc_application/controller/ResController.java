package com.denisvasilchenko.web_mvc_application.controller;


import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import com.denisvasilchenko.web_mvc_application.service.SaleService;
import com.denisvasilchenko.web_mvc_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.Collections;
import java.util.List;

@RestController
public class ResController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private UserService userService;

    @GetMapping("/store/search")
    public List<Product> search(@RequestParam String searchTerm) {
        return Collections.singletonList(productService.findProductByName(searchTerm));
    }

    @PostMapping("/store/sell")
    public ResponseEntity<String> sellProduct(@RequestBody List<Product> products) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByName(username);
        if (products==null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect product data.");
        }
        try {
            saleService.addSale(products, user);
            return ResponseEntity.status(HttpStatus.OK).body("Sale added.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
