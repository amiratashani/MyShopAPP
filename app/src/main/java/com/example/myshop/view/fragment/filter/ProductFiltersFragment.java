package com.example.myshop.view.fragment.filter;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.adapter.filter.FilterTitlesAdapter;
import com.example.myshop.databinding.FragmentProductFiltersBinding;
import com.example.myshop.view.fragment.ProductsListFragment;
import com.example.myshop.viewmodel.ProductFilterViewModel;
import com.example.myshop.viewmodel.ProductsListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFiltersFragment extends Fragment {

    private FragmentProductFiltersBinding mBinding;
    private ProductFilterViewModel mProductFilterViewModel;


    public static ProductFiltersFragment newInstance() {
        Bundle args = new Bundle();

        ProductFiltersFragment fragment = new ProductFiltersFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProductFiltersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_filters, container, false);
        mProductFilterViewModel = ViewModelProviders.of(this).get(ProductFilterViewModel.class);

        mBinding.filterBtn.setOnClickListener(v -> {
            mProductFilterViewModel.urlFilter();
            getActivity().getSupportFragmentManager().popBackStack();
        });
        return mBinding.getRoot();
    }

}
