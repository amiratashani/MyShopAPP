package com.example.myshop.controller.activity;

import android.os.Bundle;


import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myshop.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    public abstract Fragment createFragment();

    @LayoutRes
    public abstract int getLayoutResId();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createFragment())
                .addToBackStack(null)
                .commit();
    }
}
