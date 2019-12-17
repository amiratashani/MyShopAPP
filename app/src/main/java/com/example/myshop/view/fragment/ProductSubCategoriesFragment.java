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
import com.example.myshop.adapter.SubCategoriesAdapter;
import com.example.myshop.databinding.FragmentSubCategoriesBinding;
import com.example.myshop.repositories.CategoryRepository;
import com.example.myshop.viewmodel.ProductCategoriesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSubCategoriesFragment extends Fragment {
    private static final String ARG_CATEGORY_PARENT_ID = "categoryParentId";
    private ProductCategoriesViewModel mProductCategoriesViewModel;
    private FragmentSubCategoriesBinding mBinding;
    private SubCategoriesAdapter mSubCategoriesAdapter;

    private int position;

    public static ProductSubCategoriesFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_PARENT_ID, position);
        ProductSubCategoriesFragment fragment = new ProductSubCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProductSubCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_CATEGORY_PARENT_ID);
        mProductCategoriesViewModel = ViewModelProviders.of(getActivity()).get(ProductCategoriesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_categories, container, false);


        initUi();
        setSubCategoriesAdapter();

        return mBinding.getRoot();
    }

    private void initUi() {
        mBinding.fragmentSubCategoriesRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        mBinding.fragmentSubCategoriesRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setSubCategoriesAdapter() {
        mSubCategoriesAdapter = new SubCategoriesAdapter(getActivity(), this);
        mSubCategoriesAdapter.setListSubCategory(mProductCategoriesViewModel.getParentCategories().getValue().get(position).getSubCategory());
        mBinding.fragmentSubCategoriesRecyclerView.setAdapter(mSubCategoriesAdapter);
    }

}
