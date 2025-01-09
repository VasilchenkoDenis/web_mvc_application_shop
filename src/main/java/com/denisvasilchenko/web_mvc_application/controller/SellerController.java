package com.denisvasilchenko.web_mvc_application.controller;

import com.denisvasilchenko.web_mvc_application.entity.*;
import com.denisvasilchenko.web_mvc_application.reports.ReturnReport;
import com.denisvasilchenko.web_mvc_application.reports.SaleReport;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SellerController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleReport saleReport;
    @Autowired
    private ReturnReport returnReport;

    @GetMapping("/store")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String showStore(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("dailySalesAmount", saleReport.getDailySalesAmount());
        return "store";
    }

    @GetMapping("/store/addProduct")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String showAddProductForm() {
        return "addProduct";
    }

    @PostMapping("/store/addProduct")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String addProductToDataBase(@RequestParam String name, @RequestParam double price, @RequestParam double purchasePrice, @RequestParam int quantity) {
        if (name != null && quantity != 0) {
            if (productService.saveProduct(new Product(name, price, purchasePrice, quantity))) {
                return "redirect:/store";
            }
        }
        return "redirect:/store/addProduct";
    }

    @GetMapping("/store/closeForm")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String showCloseStoreForm(Model model) {
        model.addAttribute("dailySalesReport", saleReport.getDailySaleReport());
        model.addAttribute("dailyReturnReport", returnReport.getDailyReturnReport());
        model.addAttribute("totalAmount", saleReport.getDailySalesAmount());
        model.addAttribute("totalReturnAmount", returnReport.getDailyReturnAmount());
        return "closeForm";
    }

    @GetMapping("/store/return")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String showReturnForm() {
        return "return";
    }

}
