package com.example.myshop.controller.activity;


import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;


import com.example.myshop.R;
import com.example.myshop.controller.fragment.MainFragment;


public class MainActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context) {

        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getLayoutResId() {

        return R.layout.activity_main;
    }

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();

    }
    //mia

}
