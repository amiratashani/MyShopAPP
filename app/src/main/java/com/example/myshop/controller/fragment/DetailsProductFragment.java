package com.example.myshop.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myshop.R;
import com.example.myshop.controller.adapter.ImageSliderAdapter;
import com.example.myshop.model.product.Product;
import com.example.myshop.model.product.ProductImage;
import com.example.myshop.network.volley.VolleyRepository;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsProductFragment extends Fragment {
    private String mProductId;
    private Product mProduct;

    private ViewGroup sceneRoot;
    private TextView mTVNameProduct;
    private TextView mTVShortDescProduct;
    private RelativeLayout mRLExpandDescBtn;
    private TextView mTVExpandDesc;
    private TextView mTVDescProduct;

    private SliderView mSVGallery;
    private ImageSliderAdapter mAdapterSVGallery;


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
        mProductId = getArguments().getString(ARG_PRODUCT_ID_DETAILS_FRAGMENT);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_product, container, false);
        initUi(view);
        setButtonListener();
        setSliderView();
        getProduct();


        return view;
    }


    private void initUi(View view) {
        sceneRoot = view.findViewById(R.id.scene_root);

        mSVGallery = view.findViewById(R.id.fragment_details_product_sv_gallery);

        mTVNameProduct = view.findViewById(R.id.fragment_details_product_tv_nameProduct);
        mTVShortDescProduct = view.findViewById(R.id.fragment_details_product_tv_shortDescProduct);

        mRLExpandDescBtn = view.findViewById(R.id.fragment_details_product_rl_expandDescBtn);
        mTVExpandDesc = view.findViewById(R.id.fragment_details_product_tv_expandDesc);
        mTVDescProduct = view.findViewById(R.id.fragment_details_product_tv_descProduct);


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


    private void setAdapterSliderImage(List<String> urlImages) {
        mAdapterSVGallery = new ImageSliderAdapter(getActivity(), urlImages);
        mSVGallery.setSliderAdapter(mAdapterSVGallery);

    }


    private void getProduct() {
        String url = VolleyRepository.getInstance(getActivity()).getProductUrl(mProductId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Gson gson = new Gson();
                    mProduct = gson.fromJson(response.toString(), Product.class);
                    mTVDescProduct.setText(Html.fromHtml(mProduct.getDescription()));
                    mTVShortDescProduct.setText(Html.fromHtml(mProduct.getShortDescription()));
                    mTVNameProduct.setText(mProduct.getName());

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        List<String> urlsImages = mProduct.getImages()
                                .stream()
                                .map(ProductImage::getSrc)
                                .collect(Collectors.toList());
                        setAdapterSliderImage(urlsImages);
                    }

                },
                error -> {
                    mProduct = null;
                });

        VolleyRepository.getInstance(getActivity()).addToRequestQueue(request);
    }


}
