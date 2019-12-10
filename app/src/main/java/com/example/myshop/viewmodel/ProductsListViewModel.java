package com.example.myshop.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.VolleyRepository;

import com.example.myshop.repositories.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;


public class ProductsListViewModel extends AndroidViewModel {

    private MutableLiveData<String> mUrlRequest;
    private MutableLiveData<List<Product>> mProductList ;


    public ProductsListViewModel(@NonNull Application application) {
        super(application);
        mProductList= new MutableLiveData<>();
        mUrlRequest =ProductRepository.getInstance().getUrlRequest();
    }

    public LiveData<String> getUrlRequest() {
        return mUrlRequest;
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void setProductListFromApi() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlRequest.getValue(), null,
                response -> {
                    List<Product> productList = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<Product>>() {
                    }.getType());
                    mProductList.setValue(productList);
                },
                error -> {
                });

        VolleyRepository.getInstance(getApplication()).addToRequestQueue(request);
    }
}
