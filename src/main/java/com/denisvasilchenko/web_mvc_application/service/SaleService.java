package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.Sale;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.repository.SaleRepository;
import com.denisvasilchenko.web_mvc_application.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private Shop shop;

    public void addSale(List<Product> products, User user){
        saleRepository.save(shop.createSale(products, user));
    }
    public List<Sale> getSalesPerDay(LocalDate saleDate) {
        return saleRepository.findAllBySaleDateTimeBetween(saleDate.atStartOfDay(), saleDate.atTime(LocalTime.MAX));
    }
}
