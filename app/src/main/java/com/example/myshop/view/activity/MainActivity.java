package com.example.myshop.view.activity;


import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


import com.example.myshop.R;
import com.example.myshop.view.fragment.MainFragment;


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




    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
