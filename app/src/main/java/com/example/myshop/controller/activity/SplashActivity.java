package com.example.myshop.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.myshop.R;
import com.example.myshop.controller.repository.MyShopRepository;
import com.example.myshop.model.category.Category;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.VolleyRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FetcherTask fetcherTask = new FetcherTask();
        fetcherTask.execute();
    }

    private void setLatestProducts() {
        String Url = VolleyRepository.getInstance(this).getProductsUrl("date");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(this).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            MyShopRepository.getInstance(this).setLatestProducts(products);
            Log.i("kjk", "setLatestProducts: ");
        } catch (InterruptedException | ExecutionException e) {
            // exception handling
        }
    }

    private void setPopularProducts() {
        String Url = VolleyRepository.getInstance(this).getProductsUrl("popularity");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(this).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            MyShopRepository.getInstance(this).setPopularProducts(products);
            Log.i("kjk", "setPopularProducts: ");
        } catch (InterruptedException | ExecutionException e) {
            // exception handling
        }

    }

    private void setMostRateProducts() {
        String Url = VolleyRepository.getInstance(this).getProductsUrl("rating");

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(this).addToRequestQueue(request);
        try {
            List<Product> products = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Product>>() {
            }.getType());
            MyShopRepository.getInstance(this).setMostRateProducts(products);
        } catch (InterruptedException | ExecutionException e) {
            // exception handling
        }

    }

    private void getCategories() {
        String Url = VolleyRepository.getInstance(this).getCategories(String.valueOf(100));

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(this).addToRequestQueue(request);

        try {
            List<Category> categories = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Category>>() {
            }.getType());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                List<Category> parentCategories = categories.stream().filter(category -> category.getParent() == 0).collect(Collectors.toList());

                parentCategories.forEach(category ->
                        category.setSubCategory(
                                categories.stream().filter(allCategory -> allCategory.getParent() == category.getId()).collect(Collectors.toList())
                        )
                );


                MyShopRepository.getInstance(this).setParentCategory(parentCategories);
            }


        } catch (InterruptedException |
                ExecutionException e) {

        }

    }


    private class FetcherTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            setLatestProducts();
            setPopularProducts();
            setMostRateProducts();
            getCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = MainActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            finish();
        }
    }

}
