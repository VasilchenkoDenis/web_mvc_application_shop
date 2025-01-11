package com.denisvasilchenko.web_mvc_application.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "returns")
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime returnDateTime;
    @ManyToOne
    private User user;
    //будет более правильно назвать originalSale
    @ManyToOne
    private Sale sale;

    public Return() {
    }

    public Return(LocalDateTime returnDateTime, User user, Sale sale) {
        this.returnDateTime = returnDateTime;
        this.user = user;
        this.sale = sale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime dateTime) {
        this.returnDateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public List<ProductQuantity> getReturnedProducts() {
        return sale.getProducts();
    }

}
