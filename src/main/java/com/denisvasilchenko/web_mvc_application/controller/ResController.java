package com.denisvasilchenko.web_mvc_application.controller;


import com.denisvasilchenko.web_mvc_application.ErrorResponse;
import com.denisvasilchenko.web_mvc_application.entity.*;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import com.denisvasilchenko.web_mvc_application.service.ReturnService;
import com.denisvasilchenko.web_mvc_application.service.SaleService;
import com.denisvasilchenko.web_mvc_application.service.UserService;
import com.denisvasilchenko.web_mvc_application.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private UserService userService;
    @Autowired
    private Shop shop;

    @GetMapping("/products/{searchTerm}")
    public ResponseEntity<?> search(@PathVariable String searchTerm) {
        List<Product> products = productService.findProductByName(searchTerm);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            List<Product> similarProducts = productService.findSimilarProducts(searchTerm);
            String message = "Товар не существует. Возможно Вам подойдет: " + similarProducts.get(0).getName();

            ErrorResponse errorResponse = new ErrorResponse(message, similarProducts);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/sales")
    public ResponseEntity<String> addSale(@RequestBody List<Product> products) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByName(username);
        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect product data.");
        }
        try {
            saleService.addSale(products, user);
            return ResponseEntity.status(HttpStatus.OK).body("Sale added.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/sales/{selectedDate}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<List<Sale>> getSalesByDate(@PathVariable String selectedDate) {
        System.out.println(selectedDate);
        List<Sale> sales = saleService.getSalesPerDay(LocalDate.parse(selectedDate));
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/returns")
//    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> addReturn(@RequestBody Sale sale) {
//        Sale sale = saleService.getSale(Long.parseLong(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByName(username);
        if (sale == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect sale data.");
        }
        try {
            returnService.save(shop.createReturn(sale, user));
            return ResponseEntity.status(HttpStatus.OK).body("Return added.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}


