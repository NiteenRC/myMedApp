package com.fico.demo.mapper;

public class ProductVo {
    private int categoryID;
    private int productID;
    private int price;
    private String productName;
    private String categoryName;

    public ProductVo(int categoryID, int productID, int price, String productName, String categoryName) {
        super();
        this.categoryID = categoryID;
        this.productID = productID;
        this.price = price;
        this.productName = productName;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

