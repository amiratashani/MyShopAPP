package com.example.myshop.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.databinding.FragmentProductCategoriesBinding;
import com.example.myshop.model.category.Category;
import com.example.myshop.viewmodel.ProductCategoriesViewModel;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductParentCategoriesFragment extends Fragment {

    private CategoriesPagerAdapter mCategoriesPagerAdapter;
    private ProductCategoriesViewModel mProductCategoriesViewModel;
    private FragmentProductCategoriesBinding mBinding;



    public static ProductParentCategoriesFragment newInstance() {
        Bundle args = new Bundle();
        ProductParentCategoriesFragment fragment = new ProductParentCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductParentCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductCategoriesViewModel = ViewModelProviders.of(getActivity()).get(ProductCategoriesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_categories, container, false);
        setPagerAdapter();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new TabLayoutMediator( mBinding.fragmentProducCategoriestTb, mBinding.fragmentProducCategoriestVp,
                (tab, position) -> tab.setText(mProductCategoriesViewModel.getParentCategories().getValue().get(position).getName())
        ).attach();
    }


    private void setPagerAdapter() {
        mCategoriesPagerAdapter = new CategoriesPagerAdapter(this,
                mProductCategoriesViewModel.getParentCategories().getValue());
        mBinding.fragmentProducCategoriestVp.setAdapter(mCategoriesPagerAdapter);
    }


    public class CategoriesPagerAdapter extends FragmentStateAdapter {
        private List<Category> mCategoryList;

        CategoriesPagerAdapter(@NonNull Fragment fragment, List<Category> categoryList) {
            super(fragment);
            mCategoryList=categoryList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ProductSubCategoriesFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
    }


}

