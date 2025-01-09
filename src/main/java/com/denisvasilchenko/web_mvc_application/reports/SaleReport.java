package com.denisvasilchenko.web_mvc_application.reports;

import com.denisvasilchenko.web_mvc_application.entity.ProductQuantity;
import com.denisvasilchenko.web_mvc_application.entity.Sale;
import com.denisvasilchenko.web_mvc_application.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SaleReport {

    @Autowired
    private SaleService saleService;


    public String getDailySalesAmount() {
        List<Sale> sales = saleService.getSalesPerDay(LocalDate.now());
        double total = 0;
        for (Sale sale : sales) {
            List<ProductQuantity> products = sale.getProducts();
            for (ProductQuantity product : products) {
                total += (product.getProduct().getPrice() * product.getQuantity());
            }
        }
        return String.format("%.2f RUB", total);
    }

    public List<Sale> getDailySaleReport() {
        return saleService.getSalesPerDay(LocalDate.now());

    }
}
