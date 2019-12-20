package com.example.myshop.view.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.utility.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.adapter.ColorDetailAdapter;
import com.example.myshop.adapter.ImageSliderAdapter;
import com.example.myshop.databinding.FragmentProductDetailsBinding;
import com.example.myshop.model.product.Product;
import com.example.myshop.model.product.ProductAttributes;
import com.example.myshop.model.product.ProductImage;
import com.example.myshop.viewmodel.ProductBasketViewModel;
import com.example.myshop.viewmodel.ProductDetailsViewModel;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {


    private SliderView mSVGallery;
    private ImageSliderAdapter mAdapterSVGallery;
    private RecyclerView mRVColors;
    private ColorDetailAdapter mColorDetailAdapter;

    private FragmentProductDetailsBinding mBinding;
    private ProductDetailsViewModel mProductDetailsViewModel;


    public static ProductDetailsFragment newInstance() {

        Bundle args = new Bundle();
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
        mProductDetailsViewModel = ViewModelProviders.of(getActivity()).get(ProductDetailsViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        mBinding.setProductDetailsViewModel(mProductDetailsViewModel);
        mBinding.fragmentProductDetailsToolbar.setProductDetailsViewModel(mProductDetailsViewModel);



        initUi();
        mProductDetailsViewModel.getProduct().observe(this, product -> {
            setAdapterSliderImage(product);
            setAdapterRVColors(product);
        });
        setButtonListener();
        setSliderView();

        return mBinding.getRoot();
    }



    private void initUi() {
        mSVGallery = mBinding.fragmentProductDetailsSvGallery;
        mRVColors = mBinding.fragmentProductDetailsRvColor;
    }

    private void setButtonListener() {
        mBinding.fragmentProductDetailsRlExpandDescBtn.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(mBinding.sceneRoot);

            if (mBinding.fragmentProductDetailsTvDescProduct.getMaxLines() == 2) {
                mBinding.fragmentProductDetailsTvDescProduct.setMaxLines(100);
                mBinding.fragmentProductDetailsTvExpandDesc.setText(getResources().getString(R.string.collapse_text_view));
            } else {
                mBinding.fragmentProductDetailsTvDescProduct.setMaxLines(2);
                mBinding.fragmentProductDetailsTvExpandDesc.setText(getResources().getString(R.string.expand_text_view));
            }
        });

        mBinding.fragmentProductDetailRlAddToBasket.setOnClickListener(v -> {
            mProductDetailsViewModel.addProductToBasketLiveData();
            mBinding.fragmentProductDetailsToolbar.setProductDetailsViewModel(mProductDetailsViewModel);

            mBinding.executePendingBindings();
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
        mBinding.fragmentProductDetailsTvColorCount.setVisibility(View.GONE);
        mBinding.fragmentProductDetailsTvColor.setVisibility(View.GONE);
        mRVColors.setVisibility(View.GONE);
    }

    private void setAdapterSliderImage(Product product) {
        List<String> urlsImages;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            urlsImages = product.getImages()
                    .stream()
                    .map(ProductImage::getSrc)
                    .collect(Collectors.toList());
        } else {
            urlsImages = null;
        }
        mAdapterSVGallery = new ImageSliderAdapter(getActivity(), urlsImages);
        mSVGallery.setSliderAdapter(mAdapterSVGallery);

    }

    private void setAdapterRVColors(Product product) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            ProductAttributes pA = product.getAttributes().stream()
                    .filter(productAttributes -> productAttributes.getId() == 3)
                    .findAny().orElse(null);

            if (pA == null) {
                setGoneColors();
            } else {
                mBinding.fragmentProductDetailsTvColorCount.setText(getString(R.string.color_count, pA.getOptions().size()));
                setRecyclerViewColor();
                mColorDetailAdapter = new ColorDetailAdapter(pA.getOptions(), getActivity());
                mRVColors.setAdapter(mColorDetailAdapter);
            }
        }
    }


}
