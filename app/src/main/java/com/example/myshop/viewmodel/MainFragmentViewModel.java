package com.example.myshop.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myshop.model.product.Product;
import com.example.myshop.repositories.ProductRepository;
import com.example.myshop.services.BasketService;
import com.example.myshop.utility.MyShopPreferences;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mLatestProducts;
    private MutableLiveData<List<Product>> mMostRateProducts;
    private MutableLiveData<List<Product>> mPopularProducts;
    private ProductRepository mRepository;



    //private String token;

    public LiveData<List<Product>> getLatestProducts() {
        return mLatestProducts;
    }

    public LiveData<List<Product>> getMostRateProducts() {
        return mMostRateProducts;
    }

    public LiveData<List<Product>> getPopularProducts() {
        return mPopularProducts;
    }





    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mLatestProducts = mRepository.getLatestProducts();
        mMostRateProducts = mRepository.getMostRateProducts();
        mPopularProducts = mRepository.getPopularProducts();
    }

    public void setLatestProductsFromApi() {
        mRepository.setLatestProductsFromApi();
    }

    public void setMostRateProductsFromApi() {
        mRepository.setMostRateProductsFromApi();
    }

    public void setPopularProductsFromApi() {
        mRepository.setPopularProductsFromApi();
    }






    //    public void getJwt() throws JSONException {
//        String Url = "https://woocommerce.maktabsharif.ir/wp-json/jwt-auth/v1/token";
//
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("username", "amiratashanii");
//        jsonBody.put("password", "ECWX44wVBd*4YUps7f5kJUlk");
//        final String requestBody = jsonBody.toString();
//
//
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, future, future) {
//            @Override
//            public byte[] getBody() {
//                return requestBody.getBytes(StandardCharsets.UTF_8);
//            }
//        };
//
//        VolleyRepository.getInstance(getApplication()).addToRequestQueue(request);
//        try {
//
//            token = future.get().getString("token");
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i("asd", "setLatestProducts: e" + e.getMessage());
//        }
//    }
}
