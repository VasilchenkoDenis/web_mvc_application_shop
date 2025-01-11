package com.denisvasilchenko.web_mvc_application.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "sale_date_time")
    //можно назвать createdDate, то есть дата создания Sale будте более аккуратнее
    //потому что у тебя уже сущность называется Sale, зачем опять это писать
    private LocalDateTime saleDateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    private List<ProductQuantity> products;
    //советую ввести enum SaleState которое будет иметь состояния типа NEW, PAID, RETURNED
    //когда клиент только пытается купить то создается в NEW, после оплаты PAID, если запрошен возврат то RETURNED
    private boolean isReturned;

    public Sale() {
    }

    public Sale(LocalDateTime saleDateTime, User user, List<ProductQuantity> products) {
        this.saleDateTime = saleDateTime;
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(LocalDateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //почему getProduct если возвращается getProductQuantity
    public List<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantity> products) {
        this.products = products;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
