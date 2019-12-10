package com.example.myshop.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.myshop.model.product.Product;



public class ProductDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Product> mProduct;

    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);
        mProduct = new MutableLiveData<>();

    }

    public void setProduct(Product product) {
        mProduct.setValue(product);
    }

    public LiveData<Product> getProduct() {
        return mProduct;
    }



}
