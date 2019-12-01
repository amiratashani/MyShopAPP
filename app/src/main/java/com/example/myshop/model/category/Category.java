package com.example.myshop.model.category;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Category {

    @SerializedName("parent")
    private int parent;

    @SerializedName("image")
    private CategoryImage image;

    @SerializedName("menu_order")
    private int menuOrder;

    @SerializedName("display")
    private String display;

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    private List<Category> subCategory;

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }

    public void setImage(CategoryImage image) {
        this.image = image;
    }

    public CategoryImage getImage() {
        return image;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public int getMenuOrder() {
        return menuOrder;
    }


    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public String toString() {
        return
                "Category{" +
                        "parent = '" + parent + '\'' +
                        ",image = '" + image + '\'' +
                        ",menu_order = '" + menuOrder + '\'' +
                        ",display = '" + display + '\'' +
                        ",name = '" + name + '\'' +
                        ",count = '" + count + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        ",slug = '" + slug + '\'' +
                        "}";
    }
}