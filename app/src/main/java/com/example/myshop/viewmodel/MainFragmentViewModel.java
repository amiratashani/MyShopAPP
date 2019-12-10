package com.example.myshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.VolleyRepository;
import com.example.myshop.repositories.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mLatestProducts;
    private MutableLiveData<List<Product>> mMostRateProducts;
    private MutableLiveData<List<Product>> mPopularProducts;
    //private String token;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        mLatestProducts = ProductRepository.getInstance().getLatestProducts();
        mMostRateProducts = ProductRepository.getInstance().getMostRateProducts();
        mPopularProducts = ProductRepository.getInstance().getPopularProducts();
    }


    public void setLatestProducts(List<Product> latestProducts) {
        mLatestProducts.setValue(latestProducts);
    }

    public void setMostRateProducts(List<Product> mostRateProducts) {
        mMostRateProducts.setValue(mostRateProducts);
    }

    public void setPopularProducts(List<Product> popularProducts) {
        mPopularProducts.setValue(popularProducts);
    }

    public LiveData<List<Product>> getLatestProducts() {
        return mLatestProducts;
    }

    public LiveData<List<Product>> getMostRateProducts() {
        return mMostRateProducts;
    }

    public LiveData<List<Product>> getPopularProducts() {
        return mPopularProducts;
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

    public void setLatestProductsFromApi() {
        //JWT jwt = new JWT(token);
        String Url = VolleyRepository.getInstance(getApplication()).getProductsUrl("date");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future) {
        };
        VolleyRepository.getInstance(getApplication()).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            mLatestProducts.postValue(products);
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public void setMostRateProductsFromApi() {
        String Url = VolleyRepository.getInstance(getApplication()).getProductsUrl("rating");

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(getApplication()).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            mMostRateProducts.postValue(products);
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    public void setPopularProductsFromApi() {
        String Url = VolleyRepository.getInstance(getApplication()).getProductsUrl("popularity");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(getApplication()).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());

            mPopularProducts.postValue(products);
        } catch (InterruptedException | ExecutionException e) {

        }

    }
}
