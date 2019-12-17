package com.example.myshop.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myshop.model.product.Product;
import com.example.myshop.repositories.ProductRepository;
import com.example.myshop.utility.MyShopPreferences;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsViewModel extends AndroidViewModel  {

    private MutableLiveData<Product> mProduct;
    private MutableLiveData<Integer> mProductsBasketCount;
    private MutableLiveData<List<Product>> mProductsBasket;
    private ProductRepository mRepository;


    public LiveData<Integer> getProductsBasketCount() {
        return mProductsBasketCount;
    }

    public LiveData<List<Product>> getProductsBasket() {
        return mProductsBasket;
    }

    public LiveData<Product> getProduct() {
        return mProduct;
    }

    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);
        mProduct = new MutableLiveData<>();
        mRepository = ProductRepository.getInstance(getApplication());
        mProductsBasketCount = mRepository.getProductsBasketCount();
        mProductsBasket=mRepository.getProductsBasket();
    }

    public void setProduct(Product product) {
        mProduct.setValue(product);
    }






    public void addProductToBasketLiveData() {

        boolean isContain = false;
        String productId = String.valueOf(mProduct.getValue().getId());
        ArrayList<String> productsIdList = MyShopPreferences.getProductsBasket(getApplication());

        if (productsIdList == null) {
            productsIdList = new ArrayList<>();
        }

        for (String item : productsIdList) {
            if (item.equals(productId)) {
                isContain = true;
                break;
            }
        }

        if (isContain) {
        } else {
            plusCount();
            addProductToBasketLiveData(mProduct.getValue());
            productsIdList.add(productId);
            MyShopPreferences.setProductsBasket(getApplication(), productsIdList);
        }
    }

    public void plusCount() {
        int count = mProductsBasketCount.getValue();
        mProductsBasketCount.setValue(++count);

    }

    public void addProductToBasketLiveData(Product product) {
        List<Product> productsBasket = mProductsBasket.getValue();
        productsBasket.add(product);
        mProductsBasket.setValue(productsBasket);
    }


}
