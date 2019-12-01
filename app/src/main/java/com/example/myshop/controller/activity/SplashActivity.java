package com.example.myshop.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myshop.R;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.MyVolley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getCategories();


    }

    private void getCategories() {
        String Url = MyVolley.getInstance(this).getCategories();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null,
                response -> {
                    Intent intent=MainActivity.newIntent(this);
                    startActivity(intent);
                    finish();

                },
                error -> {

                    Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show();

                });
        MyVolley.getInstance(this).addToRequestQueue(request);
    }

}
