package com.example.myshop.model.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    private int id;
    private String name;
    private String description;
    @SerializedName("short_description")
    private String shortDescription;
    private String permalink;
    private String price;
    private List<ProductImage> images;
    private List<ProductAttributes> attributes;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public List<ProductAttributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributes> attributes) {
        this.attributes = attributes;
    }
}
