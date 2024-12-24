package com.denisvasilchenko.web_mvc_application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "name")
    @NotNull
    @Size(min = 2, message = "Not less than 2 symbols")
    private String name;
    private double price;
    private double purchasePrice;
    private int quantity;

    public Product() {
    }

    public Product(String name, double price, double purchasePrice, int quantity) {
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(min = 2, message = "Not less than 2 symbols") String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, message = "Not less than 2 symbols") String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
