package com.example.myshop.controller.repository;

import android.content.Context;

import com.example.myshop.model.category.Category;
import com.example.myshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class MyShopRepository {

    private static MyShopRepository mInstance;
    private Context mContext;

    private List<Product> mLatestProducts;
    private List<Product> mMostRateProducts;
    private List<Product> mPopularProducts;
    private List<Category> mParentCategory;

    public static MyShopRepository getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new MyShopRepository(context);
        }
        return mInstance;
    }

    private MyShopRepository(Context context) {
        mContext = context;
        mLatestProducts = new ArrayList<>();
        mMostRateProducts = new ArrayList<>();
        mPopularProducts = new ArrayList<>();
        mParentCategory = new ArrayList<>();
    }


    public void setLatestProducts(List<Product> latestProducts) {
        mLatestProducts = latestProducts;
    }

    public void setMostRateProducts(List<Product> mostRateProducts) {
        mMostRateProducts = mostRateProducts;
    }

    public void setPopularProducts(List<Product> popularProducts) {
        mPopularProducts = popularProducts;
    }

    public List<Product> getLatestProducts() {
        return mLatestProducts;
    }

    public List<Product> getMostRateProducts() {
        return mMostRateProducts;
    }

    public List<Product> getPopularProducts() {
        return mPopularProducts;
    }

    public List<Category> getParentCategory() {
        return mParentCategory;
    }

    public void setParentCategory(List<Category> parentCategory) {
        mParentCategory = parentCategory;
    }
}
