package com.example.myshop.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myshop.model.product.Product;
import com.example.myshop.repositories.ProductRepository;
import com.example.myshop.services.BasketService;
import com.example.myshop.utility.MyShopPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductBasketViewModel extends AndroidViewModel {
    private ProductRepository mRepository;

    private MutableLiveData<List<Product>> mProductsBasket;
    private MutableLiveData<Integer> mProductsBasketCount;

    private MutableLiveData<Double> mTotalPrice;
    private MutableLiveData<HashMap<Integer, Integer>> mProductCount;

    public LiveData<List<Product>> getProductsBasket() {
        return mProductsBasket;
    }

    public LiveData<Integer> getProductsBasketCount() {
        return mProductsBasketCount;
    }

    public LiveData<HashMap<Integer, Integer>> getProductCount() {
        return mProductCount;
    }

    public MutableLiveData<Double> getTotalPrice() {
        return mTotalPrice;
    }

    public ProductBasketViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mProductsBasket = mRepository.getProductsBasket();
        mProductsBasketCount = mRepository.getProductsBasketCount();

        mProductCount = new MutableLiveData<>();
        mTotalPrice = new MutableLiveData<>();
    }

    public void startProductBasketService() {
        mProductsBasket.setValue(new ArrayList<>());
        Intent intent = BasketService.newIntent(getApplication());
        getApplication().startService(intent);
    }

    public void setProductsBasketCount() {

        if (MyShopPreferences.getProductsBasket(getApplication()) == null) {
            mRepository.setProductsBasketCount(0);
        } else {
            int count = MyShopPreferences.getProductsBasket(getApplication()).size();
            mRepository.setProductsBasketCount(count);
        }
    }

    public void deleteFromBasket(int productId) {
        List<Product> productList = mProductsBasket.getValue();
        ArrayList<String> productsBasket = MyShopPreferences.getProductsBasket(getApplication());

        Product product = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            product = mProductsBasket.getValue().stream()
                    .filter(p -> p.getId() == productId)
                    .findFirst()
                    .get();
        }
        productList.remove(product);
        mProductsBasket.setValue(productList);

        productsBasket.remove(String.valueOf(productId));
        MyShopPreferences.setProductsBasket(getApplication(), productsBasket);

        int count = mProductsBasketCount.getValue();
        mProductsBasketCount.setValue(--count);

    }

    public void totalPriceBasketInFirst() {
        double totalPrice = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            totalPrice = mProductsBasket.getValue().stream()
                    .mapToDouble(p -> Double.valueOf(p.getPrice()))
                    .sum();
        }
        mTotalPrice.setValue(totalPrice);
    }

    public void setProductsCount() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (Product product : mProductsBasket.getValue()) {
            hashMap.put(product.getId(), 1);
        }
        mProductCount.setValue(hashMap);
    }

    public void updateCount(int id, int count) {
        if (mProductCount.getValue() != null) {
            HashMap<Integer, Integer> hashMap = mProductCount.getValue();
            hashMap.put(id, count);
            mProductCount.setValue(hashMap);
        }
    }

    public void updateTotalPrice() {
        if (mProductCount.getValue() != null) {
            HashMap<Integer, Integer> hashMap = mProductCount.getValue();
            double total = 0;

            for (Product product : mProductsBasket.getValue()) {
                double price = Double.valueOf(product.getPrice());
                double sum = price * hashMap.get(product.getId());
                total = total + sum;
            }
            mTotalPrice.setValue(total);
        }
    }

}
