package com.example.myshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myshop.R;

import com.example.myshop.viewmodel.ProductBasketViewModel;
import com.example.myshop.viewmodel.ProductCategoriesViewModel;
import com.example.myshop.viewmodel.MainFragmentViewModel;
import com.example.myshop.viewmodel.ProductFilterViewModel;


public class SplashActivity extends AppCompatActivity {

    private MainFragmentViewModel mMainFragmentViewModel;
    private ProductCategoriesViewModel mProductCategoriesViewModel;
    private ProductBasketViewModel mProductBasketViewModel;
    private ProductFilterViewModel mProductFilterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mMainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        mProductCategoriesViewModel = ViewModelProviders.of(this).get(ProductCategoriesViewModel.class);
        mProductBasketViewModel=ViewModelProviders.of(this).get(ProductBasketViewModel.class);
        mProductFilterViewModel=ViewModelProviders.of(this).get(ProductFilterViewModel.class);

        FetcherTask fetcherTask = new FetcherTask();
        fetcherTask.execute();

        mProductBasketViewModel.startProductBasketService();
        mProductBasketViewModel.setProductsBasketCount();


        mProductFilterViewModel.setAttributes();

    }


    private class FetcherTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mMainFragmentViewModel.setLatestProductsFromApi();
            mMainFragmentViewModel.setMostRateProductsFromApi();
            mMainFragmentViewModel.setPopularProductsFromApi();
            mProductCategoriesViewModel.setCategoriesFromApi();
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