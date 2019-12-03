package com.example.myshop.controller.fragment;


import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.myshop.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.controller.adapter.ProductListAdapter;
import com.example.myshop.controller.adapter.ImageSliderAdapter;
import com.example.myshop.controller.repository.MyShopRepository;
import com.example.myshop.model.product.Product;

import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final String TAG_MAIN_FRAGMENT = "MainFragment";

    private SliderView mSV;
    private RecyclerView mRVLatestProducts;
    private RecyclerView mRVPopularProducts;
    private RecyclerView mRVMostRateProducts;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageButton mIBToggleButton;

    private ImageSliderAdapter mImageSliderAdapter;
    private ProductListAdapter mLatestProductListAdapter;
    private ProductListAdapter mPopularProductListAdapter;
    private ProductListAdapter mMostRateProductListAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_drawer, container, false);
        initUi(view);
        setToolbarButtonListener();
        setSliderView();
        setAllRecyclerView();
        setLatestProducts();
        setPopularProducts();
        setMostRateProducts();
        setupDrawerContent();
        Log.i(TAG_MAIN_FRAGMENT, "onCreateView:onCreateView ");
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        mPopularProductListAdapter = null;
        mLatestProductListAdapter = null;
        mMostRateProductListAdapter = null;
    }

    private void initUi(View view) {
        mSV = view.findViewById(R.id.fragment_main_image_slider);
        mRVLatestProducts = view.findViewById(R.id.fragment_main_rv_latestProducts);
        mRVPopularProducts = view.findViewById(R.id.fragment_main_rv_popularProducts);
        mRVMostRateProducts = view.findViewById(R.id.fragment_main_rv_mostRateProducts);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        mIBToggleButton = view.findViewById(R.id.toolbar_fragment_main_ib_toggleButton);
        mNavigationView = view.findViewById(R.id.navigation);
    }

    private void setToolbarButtonListener() {
        mIBToggleButton.setOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.END));
    }

    private void setAdapterLatestProducts(List<Product> products) {
        if (mLatestProductListAdapter == null) {
            mLatestProductListAdapter = new ProductListAdapter(products, getActivity(), ProductListAdapter.Type.IN_MAIN);
            mRVLatestProducts.setAdapter(mLatestProductListAdapter);
            Log.i(TAG_MAIN_FRAGMENT, "null ");
        } else {
            mLatestProductListAdapter.setProducts(products);
            mLatestProductListAdapter.notifyDataSetChanged();
            Log.i(TAG_MAIN_FRAGMENT, "not null ");
        }
    }

    private void setAdapterPopularProducts(List<Product> products) {
        if (mPopularProductListAdapter == null) {
            mPopularProductListAdapter = new ProductListAdapter(products, getActivity(),ProductListAdapter.Type.IN_MAIN);
            mRVPopularProducts.setAdapter(mPopularProductListAdapter);
        } else {
            mPopularProductListAdapter.setProducts(products);
            mPopularProductListAdapter.notifyDataSetChanged();
        }
    }

    private void setAdapterMostRateProducts(List<Product> products) {
        if (mMostRateProductListAdapter == null) {
            mMostRateProductListAdapter = new ProductListAdapter(products, getActivity(),ProductListAdapter.Type.IN_MAIN);
            mRVMostRateProducts.setAdapter(mMostRateProductListAdapter);
        } else {
            mMostRateProductListAdapter.setProducts(products);
            mMostRateProductListAdapter.notifyDataSetChanged();

        }
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
    }

    private void setSliderView() {

        List<String> mUrl = new ArrayList<>(Arrays.asList("https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014298.jpg",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014347.jpg",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014390.jpg",
                "https://dkstatics-public.digikala.com/digikala-adservice-banners/1000014690.jpg"));

        mImageSliderAdapter = new ImageSliderAdapter(getContext(), mUrl);
        mSV.setSliderAdapter(mImageSliderAdapter);

        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mSV.setIndicatorAnimation(IndicatorAnimations.SCALE);

        mSV.setOnIndicatorClickListener(position -> mSV.setCurrentPagePosition(position));

    }

    private void setLatestProducts() {

        setAdapterLatestProducts(MyShopRepository.getInstance(getContext()).getLatestProducts());


    }

    private void setPopularProducts() {
        setAdapterPopularProducts(MyShopRepository.getInstance(getContext()).getPopularProducts());
    }

    private void setMostRateProducts() {
        setAdapterMostRateProducts(MyShopRepository.getInstance(getContext()).getMostRateProducts());
    }

    public void setupDrawerContent() {
        mNavigationView.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.nav_listProduct:
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ProductCategoriesFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
            }
            mDrawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }



}
