package com.example.myshop.view.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.adapter.ProductBasketAdapter;
import com.example.myshop.databinding.FragmentProductBasketBinding;
import com.example.myshop.utility.ProductGridItemDecoration;
import com.example.myshop.viewmodel.ProductBasketViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductBasketFragment extends Fragment {

    private FragmentProductBasketBinding mBinding;
    private ProductBasketViewModel mProductBasketViewModel;
    private ProductBasketAdapter mProductBasketAdapter;

    public static ProductBasketFragment newInstance() {

        Bundle args = new Bundle();

        ProductBasketFragment fragment = new ProductBasketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductBasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductBasketViewModel = ViewModelProviders.of(getActivity()).get(ProductBasketViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_basket, container, false);

        mProductBasketViewModel.getTotalPrice().observe(this,total->{
            mBinding.totalSalePrice.setText(String.valueOf(total));
        });
        setupAdapter();
        setupRecyclerView();



        return mBinding.getRoot();
    }

    private void setupRecyclerView() {
        mProductBasketAdapter = new ProductBasketAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);

        mBinding.fragmentProductBasketRv.setHasFixedSize(true);
        mBinding.fragmentProductBasketRv.setLayoutManager(gridLayoutManager);
        mBinding.fragmentProductBasketRv.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

    }

    private void setupAdapter() {
        mProductBasketViewModel.getProductsBasket().observe(this, products -> {
            if (products.size() == 0) {
                mBinding.fragmentProductBasketTvNullMessage.setVisibility(View.VISIBLE);
                mBinding.completeBuy.setVisibility(View.GONE);
                mBinding.totalSalePriceLy.setVisibility(View.GONE);
            } else {
                mProductBasketAdapter.setProductsBasket(products);
                mBinding.fragmentProductBasketRv.setAdapter(mProductBasketAdapter);
                mProductBasketViewModel.setProductsCount();
                mProductBasketViewModel.totalPriceBasketInFirst();
                mBinding.totalSalePrice.setText(String.valueOf(mProductBasketViewModel.getTotalPrice().getValue()));
            }
        });
    }
}
