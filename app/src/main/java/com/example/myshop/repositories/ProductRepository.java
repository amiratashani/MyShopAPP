package com.example.myshop.repositories;



import androidx.lifecycle.MutableLiveData;


import com.example.myshop.model.product.Product;

import java.util.List;

public class ProductRepository {

    private static ProductRepository mInstance;


    private MutableLiveData<List<Product>> mLatestProducts;
    private MutableLiveData<List<Product>> mMostRateProducts;
    private MutableLiveData<List<Product>> mPopularProducts;
    private MutableLiveData<String> mUrlRequest;


    public static ProductRepository getInstance( ) {
        if (mInstance == null) {
            mInstance = new ProductRepository();
        }
        return mInstance;
    }

    private ProductRepository() {
        mLatestProducts = new MutableLiveData<>();
        mMostRateProducts = new MutableLiveData<>();
        mPopularProducts = new MutableLiveData<>();

        mUrlRequest = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>> getLatestProducts() {
        return mLatestProducts;
    }
    public MutableLiveData<List<Product>> getMostRateProducts() {
        return mMostRateProducts;
    }
    public MutableLiveData<List<Product>> getPopularProducts() {
        return mPopularProducts;
    }
    public MutableLiveData<String> getUrlRequest() { return mUrlRequest; }
}
