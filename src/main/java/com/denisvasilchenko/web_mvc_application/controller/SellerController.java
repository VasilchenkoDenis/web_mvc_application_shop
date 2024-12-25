package com.denisvasilchenko.web_mvc_application.controller;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SellerController {
    @Autowired
    private ProductService productService;

    @GetMapping("/store")
    public String showStore(Model model) {
        model.addAttribute("products", productService.findAll());
        return "store";
    }

    @GetMapping("/store/addProduct")
    public String showAddProductForm() {
        return "addProduct";
    }

    @PostMapping("/store/addProduct")
    public String addProductToDataBase(@RequestParam String name, @RequestParam double price, @RequestParam double purchasePrice, @RequestParam int quantity) {
        if(name!=null&&quantity!=0){
            if(productService.saveProduct(new Product(name, price, purchasePrice, quantity))){return "redirect:/store";}
        }
        return "redirect:/store/addProduct";
    }
}
