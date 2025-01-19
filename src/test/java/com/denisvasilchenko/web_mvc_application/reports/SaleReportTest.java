package com.denisvasilchenko.web_mvc_application.reports;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.ProductQuantity;
import com.denisvasilchenko.web_mvc_application.entity.Sale;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.service.SaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SaleReportTest {

    @Mock
    private SaleService saleService;

    @InjectMocks
    public SaleReport saleReport;

    @Test
    public void returns_zero_when_no_sales() {
        //given
        given(saleService.getSalesPerDay(any())).willReturn(List.of());

        //when
        String dailySalesAmount = saleReport.getDailySalesAmount();

        //then
        assertEquals(dailySalesAmount, "0,00 RUB");
    }

    @Test
    public void returns_single_product_amount_of_single_sale() {
        //given
        given(saleService.getSalesPerDay(any())).willReturn(List.of(
                aSale(aProductQuantity("iPhone Case", 800, 2))
        ));

        //when
        String dailySalesAmount = saleReport.getDailySalesAmount();

        //then
        assertEquals(dailySalesAmount, "1600,00 RUB");
    }

    @Test
    public void returns_sum_product_amounts_of_single_sale() {
        //given
        given(saleService.getSalesPerDay(any())).willReturn(List.of(
                aSale(
                        aProductQuantity("iPhone Case", 800, 2),
                        aProductQuantity("iPhone screen protector", 200, 3)
                )
        ));

        //when
        String dailySalesAmount = saleReport.getDailySalesAmount();

        //then
        assertEquals(dailySalesAmount, "2200,00 RUB");
    }

    @Test
    public void returns_sum_product_amounts_of_sum_of_sales() {
        //given
        given(saleService.getSalesPerDay(any())).willReturn(List.of(
                aSale(
                        aProductQuantity("iPhone Case", 800, 2),
                        aProductQuantity("iPhone screen protector", 200, 3)
                ),
                aSale(aProductQuantity("iPhone 16 Pro Max 1Tb", 150000, 1))
        ));

        //when
        String dailySalesAmount = saleReport.getDailySalesAmount();

        //then
        assertEquals(dailySalesAmount, "152200,00 RUB");
    }

    private static Sale aSale(ProductQuantity... productQuantities) {
        return new Sale(
                LocalDateTime.now(),
                mock(User.class),
                Arrays.stream(productQuantities).toList()
        );
    }

    private static ProductQuantity aProductQuantity(String name, int price, int quantity) {
        return new ProductQuantity(new Product(name, price, 25, 2), quantity);
    }

}