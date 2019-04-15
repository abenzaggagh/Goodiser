package com.goodiser.modal;

public class Product {

    private String productID;
    private String title;
    private String description;
    private Double price;

    public Product() {
        this.productID = "";
        this.title = "";
        this.description = "";
        this.price = 0.0;
    }

    public Product(String title) {
        this.productID = "";
        this.title = title;

    }

    public Product(String productID, String title, String description, Double price) {
        this.productID = productID;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public String getProductID() {
        return this.productID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



}
