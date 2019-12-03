package com.example.myshop.controller.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myshop.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.controller.adapter.ColorDetailAdapter;
import com.example.myshop.controller.adapter.ImageSliderAdapter;
import com.example.myshop.controller.adapter.ProductListAdapter;
import com.example.myshop.model.product.Product;
import com.example.myshop.model.product.ProductAttributes;
import com.example.myshop.model.product.ProductImage;
import com.example.myshop.network.volley.VolleyRepository;
import com.example.myshop.utility.Utility;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {
    private String mProductId;
    private Product mProduct;
    private RelativeLayout mRLProgressBar;
    private RelativeLayout mRLScrollView;
    private ViewGroup sceneRoot;
    private TextView mTVNameProduct;
    private TextView mTVShortDescProduct;
    private TextView mTVPayablePrice;
    private RelativeLayout mRLExpandDescBtn;
    private TextView mTVExpandDesc;
    private TextView mTVDescProduct;
    private TextView mTVColorCount;
    private TextView mTVColor;


    private SliderView mSVGallery;
    private ImageSliderAdapter mAdapterSVGallery;
    private RecyclerView mRVColors;
    private ColorDetailAdapter mColorDetailAdapter;


    public static final String ARG_PRODUCT_ID_DETAILS_FRAGMENT = "productIdDetailsFragment";

    public static ProductDetailsFragment newInstance(String productId) {

        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID_DETAILS_FRAGMENT, productId);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getString(ARG_PRODUCT_ID_DETAILS_FRAGMENT);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        initUi(view);
        getProduct();
        setButtonListener();
        setSliderView();
        return view;
    }


    private void initUi(View view) {
        sceneRoot = view.findViewById(R.id.scene_root);


        mRLProgressBar=view.findViewById(R.id.fragment_product_details_rl_progressBar);
        mRLScrollView=view.findViewById(R.id.fragment_product_details_rl_scrollView);


        mSVGallery = view.findViewById(R.id.fragment_product_details_sv_gallery);

        mTVNameProduct = view.findViewById(R.id.fragment_product_details_tv_nameProduct);
        mTVShortDescProduct = view.findViewById(R.id.fragment_product_details_tv_shortDescProduct);
        mTVPayablePrice = view.findViewById(R.id.fragment_product_details_tv_payablePrice);

        mRLExpandDescBtn = view.findViewById(R.id.fragment_product_details_rl_expandDescBtn);
        mTVExpandDesc = view.findViewById(R.id.fragment_product_details_tv_expandDesc);
        mTVDescProduct = view.findViewById(R.id.fragment_product_details_tv_descProduct);

        mTVColorCount = view.findViewById(R.id.fragment_product_details_tv_color_count);
        mTVColor = view.findViewById(R.id.fragment_product_details_tv_color);
        mRVColors = view.findViewById(R.id.fragment_product_details_rv_color);

    }

    private void setButtonListener() {

        mRLExpandDescBtn.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(sceneRoot);

            if (mTVDescProduct.getMaxLines() == 8) {
                mTVDescProduct.setMaxLines(100);
                mTVExpandDesc.setText(getResources().getString(R.string.collapse_text_view));
            } else {
                mTVDescProduct.setMaxLines(8);
                mTVExpandDesc.setText(getResources().getString(R.string.expand_text_view));
            }

        });
    }

    private void setSliderView() {
        mSVGallery.setIndicatorAnimation(IndicatorAnimations.WORM);
    }

    private void setRecyclerViewColor() {
        mRVColors.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        mRVColors.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setReverseLayout(true);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);
        mRVColors.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    private void setGoneColors() {
        mTVColorCount.setVisibility(View.GONE);
        mTVColor.setVisibility(View.GONE);
        mRVColors.setVisibility(View.GONE);
    }


    private void setAdapterSliderImage() {
        List<String> urlsImages;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            urlsImages = mProduct.getImages()
                    .stream()
                    .map(ProductImage::getSrc)
                    .collect(Collectors.toList());
        } else {
            urlsImages = null;
        }
        mAdapterSVGallery = new ImageSliderAdapter(getActivity(), urlsImages);
        mSVGallery.setSliderAdapter(mAdapterSVGallery);

    }

    private void setAdapterRVColors() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            ProductAttributes pA = mProduct.getAttributes().stream()
                    .filter(productAttributes -> productAttributes.getId() == 1)
                    .findAny().orElse(null);

            if (pA == null) {
                setGoneColors();
            } else {
                mTVColorCount.setText(getString(R.string.color_count, pA.getOptions().size()));
                setRecyclerViewColor();
                mColorDetailAdapter = new ColorDetailAdapter(pA.getOptions(), getActivity());
                mRVColors.setAdapter(mColorDetailAdapter);
            }
        }


    }

    private void getProduct() {
        String url = VolleyRepository.getInstance(getActivity()).getProductUrl(mProductId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            mProduct = new Gson().fromJson(response.toString(), Product.class);
            mTVDescProduct.setText(Html.fromHtml(mProduct.getDescription()));
            mTVShortDescProduct.setText(Html.fromHtml(mProduct.getShortDescription()));
            mTVNameProduct.setText(mProduct.getName());
            mTVPayablePrice.setText(Utility.addCommaInToNumber().format(Integer.valueOf(mProduct.getPrice())));
            setAdapterSliderImage();
            setAdapterRVColors();
            mRLProgressBar.setVisibility(View.GONE);
            mRLScrollView.setVisibility(View.VISIBLE);
        }, error -> {

        });
        VolleyRepository.getInstance(getActivity()).addToRequestQueue(request);


    }





}
