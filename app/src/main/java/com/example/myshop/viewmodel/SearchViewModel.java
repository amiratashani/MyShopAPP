package com.example.myshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.example.myshop.network.volley.VolleyRepository;
import com.example.myshop.repositories.ProductRepository;

public class SearchViewModel extends AndroidViewModel {

    private ProductRepository mRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository=ProductRepository.getInstance(getApplication());
    }
    public void setUrlRequest(String search){
        String searchText= VolleyRepository.getInstance(getApplication()).getProductsSearchUrl(search);
        mRepository.setUrlRequest(searchText);
    }

}
