package com.example.myshop.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.myshop.repositories.ProductRepository;


public class BasketService extends IntentService {

    public static Intent newIntent(Context context) {
        return new Intent(context, BasketService.class);
    }

    public BasketService() {
        super("BasketService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (isNetworkAvailableAndConnected())
//            return;

       ProductRepository.getInstance(this).setProductsBasketFromApi();


        Log.i("mdaoshdn",  ProductRepository.getInstance(this).getProductsBasket().getValue().toString());
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isAvailable = cm.getActiveNetworkInfo() != null;
        boolean isConnected = isAvailable && cm.getActiveNetworkInfo().isConnected();
        return isConnected;
    }

}
