package com.example.myshop.controller.fragment;


import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myshop.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.controller.adapter.ProductMainAdapter;
import com.example.myshop.controller.adapter.ImageSliderAdapter;
import com.example.myshop.controller.repository.MyShopRepository;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.VolleyRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.lang.reflect.Type;
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
    private ImageSliderAdapter mImageSliderAdapter;

    private DrawerLayout mDrawerLayout;
    private ImageButton mIBToggleButton;

    private ProductMainAdapter mLatestProductMainAdapter;
    private ProductMainAdapter mPopularProductMainAdapter;
    private ProductMainAdapter mMostRateProductMainAdapter;

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
        Log.i(TAG_MAIN_FRAGMENT, "onCreateView:onCreateView ");
        return view;
    }


    private void initUi(View view) {
        mSV = view.findViewById(R.id.fragment_main_image_slider);
        mRVLatestProducts = view.findViewById(R.id.fragment_main_rv_latestProducts);
        mRVPopularProducts = view.findViewById(R.id.fragment_main_rv_popularProducts);
        mRVMostRateProducts = view.findViewById(R.id.fragment_main_rv_mostRateProducts);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        mIBToggleButton = view.findViewById(R.id.toolbar_fragment_main_ib_toggleButton);

    }

    private void setToolbarButtonListener() {
        mIBToggleButton.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.RIGHT));
    }

    private void setAdapterLatestProducts(List<Product> products) {
        if (mLatestProductMainAdapter == null) {
            mLatestProductMainAdapter = new ProductMainAdapter(products, getActivity());
            mRVLatestProducts.setAdapter(mLatestProductMainAdapter);
            Log.i(TAG_MAIN_FRAGMENT, "null ");
        } else {
            mLatestProductMainAdapter.setProducts(products);
            mLatestProductMainAdapter.notifyDataSetChanged();
            Log.i(TAG_MAIN_FRAGMENT, "not null ");
        }
    }

    private void setAdapterPopularProducts(List<Product> products) {
        if (mPopularProductMainAdapter == null) {
            mPopularProductMainAdapter = new ProductMainAdapter(products, getActivity());
            mRVPopularProducts.setAdapter(mPopularProductMainAdapter);
        } else {
            mPopularProductMainAdapter.setProducts(products);
            mPopularProductMainAdapter.notifyDataSetChanged();
        }
    }

    private void setAdapterMostRateProducts(List<Product> products) {
        if (mMostRateProductMainAdapter == null) {
            mMostRateProductMainAdapter = new ProductMainAdapter(products, getActivity());
            mRVMostRateProducts.setAdapter(mMostRateProductMainAdapter);
        } else {
            mMostRateProductMainAdapter.setProducts(products);
            mMostRateProductMainAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPopularProductMainAdapter = null;
        mLatestProductMainAdapter = null;
        mMostRateProductMainAdapter = null;
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


}
