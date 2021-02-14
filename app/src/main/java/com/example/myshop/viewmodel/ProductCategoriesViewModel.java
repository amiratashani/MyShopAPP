package com.example.myshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;

import com.example.myshop.model.category.Category;
import com.example.myshop.network.volley.VolleyRepository;
import com.example.myshop.repositories.CategoryRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ProductCategoriesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Category>> mParentCategories;
    private MutableLiveData<Integer> mCurent;

    public ProductCategoriesViewModel(@NonNull Application application) {
        super(application);
        mParentCategories = CategoryRepository.getInstance().getParentCategories();
    }

    public LiveData<List<Category>> getParentCategories() {
        return mParentCategories;
    }

    public void setParentCategories(List<Category> parentCategories) {
        mParentCategories.setValue(parentCategories);
    }

    public void setCategoriesFromApi() {
        String Url = VolleyRepository.getInstance(getApplication()).getCategories(String.valueOf(100));
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, future, future);
        VolleyRepository.getInstance(getApplication()).addToRequestQueue(request);
        try {
            List<Category> allCategories = new Gson().fromJson(future.get().toString(), new TypeToken<ArrayList<Category>>() {
            }.getType());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                List<Category> productCategories = allCategories.stream().filter(category -> category.getParent() == 0).collect(Collectors.toList());
                productCategories.forEach(category ->
                        category.setSubCategory(
                                allCategories.stream().filter(allCategory -> allCategory.getParent() == category.getId()).collect(Collectors.toList())
                        )
                );
                mParentCategories.postValue(productCategories);
            }

        } catch (InterruptedException | ExecutionException e) {

        }

    }



}
