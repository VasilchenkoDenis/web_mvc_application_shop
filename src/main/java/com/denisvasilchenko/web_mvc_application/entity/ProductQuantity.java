package com.denisvasilchenko.web_mvc_application.entity;

import jakarta.persistence.*;

//мне кажется стоит подумать над названием этой сущности и ее целесобразностью
//я вначале подумал что оно обозначает сколько у тебя товаров на складе
//но на деле это количество товаров которое находится в Sale

//по большому счету это все относится к Sale, можешь попробовать сделать как тут
//https://github.com/gunseful/fanstastic-four-on-spring-boot/blob/8b8aec120b8555d4e2bb10a4b032301288babc35/src/main/java/fantasticfour/entity/Order.java#L26
@Entity
public class ProductQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Product product;
    private int quantity;

    public ProductQuantity() {
    }

    public ProductQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
