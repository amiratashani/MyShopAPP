package com.example.myshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myshop.R;

import com.example.myshop.viewmodel.ProductCategoriesViewModel;
import com.example.myshop.viewmodel.MainFragmentViewModel;



public class SplashActivity extends AppCompatActivity {

    private MainFragmentViewModel mMainFragmentViewModel;
    private ProductCategoriesViewModel mProductCategoriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mMainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        mProductCategoriesViewModel = ViewModelProviders.of(this).get(ProductCategoriesViewModel.class);
        FetcherTask fetcherTask = new FetcherTask();
        fetcherTask.execute();
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
