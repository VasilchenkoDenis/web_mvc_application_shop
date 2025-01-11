package com.denisvasilchenko.web_mvc_application;

import com.denisvasilchenko.web_mvc_application.entity.Product;

import java.util.List;

public class ErrorResponse {
    private String message;
    private List<Product> similarProducts;

    //todo комментарий
    public ErrorResponse(String message, List<Product> similarProducts) {
        this.message = message;
        this.similarProducts = similarProducts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(List<Product> similarProducts) {
        this.similarProducts = similarProducts;
    }
}
