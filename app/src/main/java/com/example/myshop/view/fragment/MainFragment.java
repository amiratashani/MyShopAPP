package com.example.myshop.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myshop.utility.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.adapter.ProductListMainAdapter;
import com.example.myshop.adapter.ImageSliderAdapter;
import com.example.myshop.databinding.NavigationDrawerBinding;

import com.example.myshop.viewmodel.MainFragmentViewModel;

import com.example.myshop.viewmodel.ProductBasketViewModel;
import com.example.myshop.viewmodel.ProductFilterViewModel;
import com.smarteist.autoimageslider.IndicatorAnimations;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    private NavigationDrawerBinding mBinding;
    private MainFragmentViewModel mMainFragmentViewModel;
    private ProductBasketViewModel mProductBasketViewModel;
    private ProductFilterViewModel mProductFilterViewModel;

    private ImageSliderAdapter mImageSliderAdapter;
    private ProductListMainAdapter mLatestProductListMainAdapter;
    private ProductListMainAdapter mPopularProductListMainAdapter;
    private ProductListMainAdapter mMostRateProductListMainAdapter;

    private RecyclerView mRVLatestProducts;
    private RecyclerView mRVPopularProducts;
    private RecyclerView mRVMostRateProducts;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        mProductBasketViewModel=ViewModelProviders.of(this).get(ProductBasketViewModel.class);
        mProductFilterViewModel=ViewModelProviders.of(this).get(ProductFilterViewModel.class);
        mProductFilterViewModel.setSizeFilter();
        mProductFilterViewModel.setColorFilter();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.navigation_drawer, container, false);
        mBinding.includeFragmentMain.includeToolbarFragmentMain.setProductBasketViewModel(mProductBasketViewModel);


        setLatestProductsAdapter();
        setPopularProductsAdapter();
        setMostRateProductsAdapter();

        initUi();
        setToolbarButtonListener();
        setSliderView();

        setAllRecyclerView();
        setupDrawerContent();

        return mBinding.getRoot();
    }


    private void initUi() {
        mRVLatestProducts = mBinding.includeFragmentMain.fragmentMainRvLatestProducts;
        mRVPopularProducts = mBinding.includeFragmentMain.fragmentMainRvPopularProducts;
        mRVMostRateProducts = mBinding.includeFragmentMain.fragmentMainRvMostRateProducts;
    }

    private void setToolbarButtonListener() {
        mBinding.includeFragmentMain.includeToolbarFragmentMain.toolbarFragmentMainIbToggleButton
                .setOnClickListener(v -> mBinding.drawerLayout.openDrawer(GravityCompat.END));
        mBinding.includeFragmentMain.includeToolbarFragmentMain.toolbarFragmentMainIbSearchIcButton
                .setOnClickListener(v -> {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, SearchFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                });
        mBinding.includeFragmentMain.includeToolbarFragmentMain.toolbarFragmentMainIvBasketButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,ProductBasketFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void setAllRecyclerView() {
        mRVLatestProducts.setHasFixedSize(true);
        mRVPopularProducts.setHasFixedSize(true);
        mRVMostRateProducts.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setReverseLayout(true);
        gridLayoutManager1.setReverseLayout(true);
        gridLayoutManager2.setReverseLayout(true);

        mRVLatestProducts.setLayoutManager(gridLayoutManager);
        mRVPopularProducts.setLayoutManager(gridLayoutManager1);
        mRVMostRateProducts.setLayoutManager(gridLayoutManager2);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);

        mRVLatestProducts.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
        mRVPopularProducts.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
        mRVMostRateProducts.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        mRVLatestProducts.setAdapter(mLatestProductListMainAdapter);
        mRVPopularProducts.setAdapter(mPopularProductListMainAdapter);
        mRVMostRateProducts.setAdapter(mMostRateProductListMainAdapter);
    }

    private void setSliderView() {

        List<String> mUrl = new ArrayList<>(Arrays.asList("https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014298.jpg",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014347.jpg",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014390.jpg",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014690.jpg"));

        mImageSliderAdapter = new ImageSliderAdapter(getContext(), mUrl);
        mBinding.includeFragmentMain.fragmentMainImageSlider.setSliderAdapter(mImageSliderAdapter);

        mBinding.includeFragmentMain.fragmentMainImageSlider.setIndicatorAnimation(IndicatorAnimations.SCALE);

        mBinding.includeFragmentMain.fragmentMainImageSlider.setOnIndicatorClickListener(position ->
                mBinding.includeFragmentMain.fragmentMainImageSlider.setCurrentPagePosition(position));

    }

    private void setLatestProductsAdapter() {
        mLatestProductListMainAdapter = new ProductListMainAdapter(getActivity());
        mMainFragmentViewModel.getLatestProducts().observe(this, products -> {
            mLatestProductListMainAdapter.setProducts(products);
        });

    }

    private void setPopularProductsAdapter() {
        mPopularProductListMainAdapter = new ProductListMainAdapter(getActivity());
        mMainFragmentViewModel.getPopularProducts().observe(this, products -> {
            mPopularProductListMainAdapter.setProducts(products);
        });
    }

    private void setMostRateProductsAdapter() {
        mMostRateProductListMainAdapter = new ProductListMainAdapter(getActivity());
        mMainFragmentViewModel.getMostRateProducts().observe(this, products -> {
            mMostRateProductListMainAdapter.setProducts(products);
        });
    }

    private void setupDrawerContent() {
        mBinding.navigation.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_listProduct:
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ProductParentCategoriesFragment.newInstance(), "productCategoriesFragment")
                            .addToBackStack("productCategoriesFragment")
                            .commit();
                    break;
            }
            mBinding.drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }

}
