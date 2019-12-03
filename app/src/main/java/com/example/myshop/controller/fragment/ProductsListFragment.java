package com.example.myshop.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myshop.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.controller.adapter.ProductListAdapter;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.VolleyRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsListFragment extends Fragment {

    public static final String ARG_URL_REQUEST_PRODUCT_LIST = "urlRequestProductList";
    private String mRequestUrl;

    private RecyclerView mRVProductsList;
    private ProductListAdapter mProductListAdapter;
    private ProgressBar mProgressBar;

    public static ProductsListFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(ARG_URL_REQUEST_PRODUCT_LIST, url);

        ProductsListFragment fragment = new ProductsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestUrl = getArguments().getString(ARG_URL_REQUEST_PRODUCT_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        initUi(view);
        setRVProductsListLayout();
        getList();
        return view;
    }

    private void initUi(View view) {
        mRVProductsList = view.findViewById(R.id.fragment_products_list_rv);
        mProgressBar=view.findViewById(R.id.fragment_product_list_pb);
    }

    private void setRVProductsListLayout(){
        mRVProductsList.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        mRVProductsList.setLayoutManager(gridLayoutManager);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);
        mRVProductsList.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

    }

    private void setProductListAdapter(List<Product> productList) {
        if (mProductListAdapter == null) {
            mProductListAdapter = new ProductListAdapter(productList, getActivity(), ProductListAdapter.Type.IN_LIST);
            mRVProductsList.setAdapter(mProductListAdapter);
        } else {
            mProductListAdapter.setProducts(productList);
            mProductListAdapter.notifyDataSetChanged();
        }
    }


    private void getList() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mRequestUrl, null,
                response -> {

                    List<Product> productList = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<Product>>() {
                    }.getType());

                    setProductListAdapter(productList);
                    mProgressBar.setVisibility(View.GONE);
                    mRVProductsList.setVisibility(View.VISIBLE);
                },
                error -> {
                });

        VolleyRepository.getInstance(getActivity()).addToRequestQueue(request);
    }

}
