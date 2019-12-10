package com.example.myshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myshop.model.category.Category;
import com.example.myshop.network.volley.VolleyRepository;
import com.example.myshop.repositories.ProductRepository;

public class CategoryViewModel extends AndroidViewModel {

    private MutableLiveData<Category> mCategory;
    private MutableLiveData<String> mUrlRequest;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mCategory = new MutableLiveData<>();
        mUrlRequest = ProductRepository.getInstance().getUrlRequest();
    }

    public LiveData<Category> getCategory() {
        return mCategory;
    }
    public void setCategory(Category category) {
        mCategory.setValue(category);
    }

    public LiveData<String> getUrlRequest() {
        return mUrlRequest;
    }
    public void setUrlRequest(String urlRequest) { mUrlRequest.setValue(urlRequest); }


    public void generateUrlRequest() {
        String urlRequest = VolleyRepository.getInstance(getApplication()).getProductWithCategory(String.valueOf(mCategory.getValue().getId()));
       setUrlRequest(urlRequest);
    }
}
