package com.denisvasilchenko.web_mvc_application.reports;

import com.denisvasilchenko.web_mvc_application.entity.ProductQuantity;
import com.denisvasilchenko.web_mvc_application.entity.Return;
import com.denisvasilchenko.web_mvc_application.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReturnReport {

    @Autowired
    private ReturnService returnService;

    public List<Return> getDailyReturnReport() {
        return returnService.getReturnsPerDay(LocalDate.now());
    }

    public String getDailyReturnAmount() {
        List<Return> returns = returnService.getReturnsPerDay(LocalDate.now());
        double total = 0;
        for (Return r : returns) {
            List<ProductQuantity> products = r.getReturnedProducts();
            for (ProductQuantity p : products) {
                total += (p.getProduct().getPrice() * p.getQuantity());
            }
        }
        return String.format("%.2f RUB", total);
    }
}
