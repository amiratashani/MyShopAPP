package com.example.myshop.repositories;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.VolleyRepository;
import com.example.myshop.services.BasketService;
import com.example.myshop.utility.MyShopPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProductRepository {

    private static ProductRepository mInstance;
    private Context mContext;


    private MutableLiveData<List<Product>> mLatestProducts;
    private MutableLiveData<List<Product>> mMostRateProducts;
    private MutableLiveData<List<Product>> mPopularProducts;
    private MutableLiveData<String> mUrlRequest;

    private MutableLiveData<List<Product>> mProductsBasket;
    private MutableLiveData<Integer> mProductsBasketCount;


    public static ProductRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ProductRepository(context);
        }
        return mInstance;
    }

    private ProductRepository(Context context) {
        mLatestProducts = new MutableLiveData<>();
        mMostRateProducts = new MutableLiveData<>();
        mPopularProducts = new MutableLiveData<>();
        mUrlRequest = new MutableLiveData<>();

        mProductsBasket = new MutableLiveData<>();
        mProductsBasketCount = new MutableLiveData<>();

        mContext = context;
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

    public MutableLiveData<String> getUrlRequest() {
        return mUrlRequest;
    }

    public MutableLiveData<List<Product>> getProductsBasket() {
        return mProductsBasket;
    }

    public MutableLiveData<Integer> getProductsBasketCount() {
        return mProductsBasketCount;
    }

    public void setLatestProductsFromApi() {
        String Url = VolleyRepository.getInstance(mContext).getProductsUrl("date");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future) {
        };
        VolleyRepository.getInstance(mContext).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            mLatestProducts.postValue(products);
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public void setMostRateProductsFromApi() {
        String Url = VolleyRepository.getInstance(mContext).getProductsUrl("rating");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(mContext).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            mMostRateProducts.postValue(products);
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    public void setPopularProductsFromApi() {
        String Url = VolleyRepository.getInstance(mContext).getProductsUrl("popularity");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(mContext).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());

            mPopularProducts.postValue(products);
        } catch (InterruptedException | ExecutionException e) {

        }

    }

    public void setUrlRequest(String urlRequest) {
        mUrlRequest.setValue(urlRequest);
    }


    public void setProductsBasketCount(Integer productsBasketCount) {
        mProductsBasketCount.setValue(productsBasketCount);
    }

    public void setProductsBasketFromApi() {
        if (MyShopPreferences.getProductsBasket(mContext) != null) {
            ArrayList<String> productsId = MyShopPreferences.getProductsBasket(mContext);
            productsId.add(0, "");
            String str = productsId.toString();
            String Url = VolleyRepository.getInstance(mContext).getProductsBasket() + "&include=" + str;
            RequestFuture<JSONArray> future = RequestFuture.newFuture();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return super.getParams();
                }
            };
            VolleyRepository.getInstance(mContext).addToRequestQueue(request);
            try {
                List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
                }.getType());

                ProductRepository.getInstance(mContext).getProductsBasket().postValue(products);
            } catch (InterruptedException | ExecutionException e) {

            }
        }
    }

}
