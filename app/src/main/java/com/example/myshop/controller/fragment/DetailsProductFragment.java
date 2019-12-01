package com.example.myshop.controller.fragment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myshop.R;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.MyVolley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsProductFragment extends Fragment {
    private String mProductId;
    private Product mProduct;

    private TextView mTextViewDecProduct;
    private NestedScrollView mNestedScrollView;
    private AppBarLayout mToolbarDetailsFragment;


    public static final String ARG_PRODUCT_ID_DETAILS_FRAGMENT = "productIdDetailsFragment";

    public static DetailsProductFragment newInstance(String productId) {

        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID_DETAILS_FRAGMENT, productId);
        DetailsProductFragment fragment = new DetailsProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_details_product, container, false);

        initUi(view);


        return view;
    }


    private void initUi(View view) {
        mTextViewDecProduct = view.findViewById(R.id.fragment_details_product_tv_desc);
        mNestedScrollView = view.findViewById(R.id.fragment_details_product_nsv);
        mToolbarDetailsFragment = view.findViewById(R.id.fragment_details_product_abl);



    }





    private void getProduct() {
        String url = MyVolley.getInstance(getActivity()).getProductUrl(mProductId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Gson gson = new Gson();
                    mProduct = gson.fromJson(response.toString(), Product.class);
                    mTextViewDecProduct.setText(mProduct.getDescription());
                },
                error -> {
                    mProduct = null;
                });

        MyVolley.getInstance(getActivity()).addToRequestQueue(request);
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
