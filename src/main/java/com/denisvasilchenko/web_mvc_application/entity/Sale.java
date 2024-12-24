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
    @Column(name="sale_date_time")
    private LocalDateTime saleDateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    private List<Product> products;

    public Sale() {
    }

    public Sale(LocalDateTime saleDateTime, User user, List<Product> products) {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
