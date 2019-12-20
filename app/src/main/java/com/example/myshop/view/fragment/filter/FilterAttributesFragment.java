package com.example.myshop.view.fragment.filter;


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
import com.example.myshop.adapter.filter.FilterAttributesAdapter;
import com.example.myshop.databinding.FragmentFilterAttributesBinding;
import com.example.myshop.viewmodel.ProductFilterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterAttributesFragment extends Fragment {
    private FragmentFilterAttributesBinding mBinding;
    private ProductFilterViewModel mProductFilterViewModel;
    private FilterAttributesAdapter mFilterAttributesAdapter;

    public static FilterAttributesFragment newInstance() {
        Bundle args = new Bundle();

        FilterAttributesFragment fragment = new FilterAttributesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterAttributesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_attributes, container, false);
        mProductFilterViewModel = ViewModelProviders.of(getActivity()).get(ProductFilterViewModel.class);
        mFilterAttributesAdapter = new FilterAttributesAdapter(getActivity());

        mProductFilterViewModel.getTypeAdapter().observe(this, integer -> {
            mFilterAttributesAdapter.setType(integer);
        });
        mProductFilterViewModel.getAttributeId().observe(this, integer -> {
            mFilterAttributesAdapter.setAttributeTerms(mProductFilterViewModel.getAttributeTerms());
        });


        setupViews();

        return mBinding.getRoot();
    }

    private void setupViews() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mBinding.recyclerViewFilterAttributes.setLayoutManager(gridLayoutManager);
        mBinding.recyclerViewFilterAttributes.setAdapter(mFilterAttributesAdapter);
        mBinding.recyclerViewFilterAttributes.setOverScrollMode(2);
    }

}
