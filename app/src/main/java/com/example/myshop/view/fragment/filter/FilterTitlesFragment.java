package com.example.myshop.view.fragment.filter;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.adapter.filter.FilterTitlesAdapter;
import com.example.myshop.databinding.FragmentFilterTitlesBinding;
import com.example.myshop.viewmodel.ProductFilterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterTitlesFragment extends Fragment {
    private FragmentFilterTitlesBinding mBinding;
    private FilterTitlesAdapter mFilterTitlesAdapter;
    private ProductFilterViewModel mProductFilterViewModel;

    public static FilterTitlesFragment newInstance() {

        Bundle args = new Bundle();

        FilterTitlesFragment fragment = new FilterTitlesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterTitlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductFilterViewModel= ViewModelProviders.of(getParentFragment()).get(ProductFilterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_titles, container, false);
        mFilterTitlesAdapter = new FilterTitlesAdapter(getActivity(),mProductFilterViewModel);
        mProductFilterViewModel.getAttributes().observe(this,attributes -> {
            mFilterTitlesAdapter.setAttributes(attributes);
            mProductFilterViewModel.setAttributeId(attributes.get(0).getId());
            mProductFilterViewModel.setTypeAdapter(1);
        });
        setupViews();
        return mBinding.getRoot();
    }

    private void setupViews() {
        mBinding.listFilterTitles.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mBinding.listFilterTitles.setAdapter(mFilterTitlesAdapter);
        mBinding.listFilterTitles.setOverScrollMode(2);
    }

}
