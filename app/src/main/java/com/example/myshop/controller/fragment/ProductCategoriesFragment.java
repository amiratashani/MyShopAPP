package com.example.myshop.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.controller.repository.MyShopRepository;
import com.example.myshop.model.category.Category;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCategoriesFragment extends Fragment {
    private ViewPager mVPCategories;
    private TabLayout mTLCategories;
    private CategoriesPagerAdapter mCategoriesPagerAdapter;

    public static ProductCategoriesFragment newInstance() {

        Bundle args = new Bundle();

        ProductCategoriesFragment fragment = new ProductCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_categories, container, false);

        initUi(view);
        setPagerAdapter();

        return view;
    }

    private void initUi(View view) {
        mVPCategories = view.findViewById(R.id.fragment_produc_categoriest_vp);
        mTLCategories = view.findViewById(R.id.fragment_produc_categoriest_tb);
        mTLCategories.setupWithViewPager(mVPCategories);
    }

    private void setPagerAdapter() {
        mCategoriesPagerAdapter = new CategoriesPagerAdapter(getActivity().getSupportFragmentManager(),
                MyShopRepository.getInstance(getActivity()).getParentCategory());
        mVPCategories.setAdapter(mCategoriesPagerAdapter);
    }


    private class CategoriesPagerAdapter extends FragmentStatePagerAdapter {


        List<Category> mCategoryList;

        public CategoriesPagerAdapter(@NonNull FragmentManager fm, List<Category> categoryList) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mCategoryList = categoryList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            int categoryId = mCategoryList.get(position).getId();
            return SubCategoriesFragment.newInstance(categoryId);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mCategoryList.get(position).getName();
        }

        @Override
        public int getCount() {
            return mCategoryList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            // POSITION_NONE means something like: this fragment is no longer valid
            // triggering the ViewPager to re-build the instance of this fragment.
            return POSITION_NONE;
        }
    }

}

