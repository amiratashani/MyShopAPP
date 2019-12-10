package com.example.myshop.view.activity;


import android.content.Context;
import android.content.Intent;

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
//            int index = getSupportFragmentManager().getBackStackEntryCount()-1;
//            String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();
//            if (tag.equals("productCategoriesFragment")) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, ProductParentCategoriesFragment.newInstance(), "productCategoriesFragment")
//                        .addToBackStack("productCategoriesFragment")
//                        .commit();
            getSupportFragmentManager().popBackStack();

        } else {
            finish();
        }


    }

    private void tellFragments() {

    }
}
