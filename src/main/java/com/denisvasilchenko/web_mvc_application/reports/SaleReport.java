package com.denisvasilchenko.web_mvc_application.reports;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.Sale;
import com.denisvasilchenko.web_mvc_application.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SaleReport {
    private List<Sale> sales;
    @Autowired
    private SaleService saleService;

    public SaleReport(List<Sale> sales) {
        this.sales = sales;
    }

    public String getDailySalesAmount (){
        List<Sale> sales = saleService.getSalesPerDay(LocalDate.now());
        double total = 0;
        for(Sale sale: sales){
            List<Product> products = sale.getProducts();
            for(Product product: products){
                total+=product.getPrice();
            }
        }
        return String.format("%.2f RUB", total);
    }
}
