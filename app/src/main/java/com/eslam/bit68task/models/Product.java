package com.eslam.bit68task.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("product_img")
    @Expose
    private String productImg;

    /**
     * No args constructor for use in serialization
     */
    public Product() {
    }

    /**
     * @param price
     * @param name
     * @param productImg
     * @param weight
     * @param id
     */
    public Product(String id, String name, String weight, String price, String productImg) {
        super();
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.productImg = productImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

}