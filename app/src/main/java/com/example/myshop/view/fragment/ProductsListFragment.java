package com.example.myshop.view.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.utility.ProductGridItemDecoration;
import com.example.myshop.R;
import com.example.myshop.adapter.ProductListAdapter;
import com.example.myshop.databinding.FragmentProductsListBinding;
import com.example.myshop.view.fragment.filter.ProductFiltersFragment;
import com.example.myshop.view.fragment.filter.SortDialogFragment;
import com.example.myshop.viewmodel.ProductsListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsListFragment extends Fragment {
    public static final String SORT_DIALOG_FRAGMENT = "sort_dialog_fragment";
    private static final int REQUEST_CODE_FOR_SORT_DIALOG = 1;
    private FragmentProductsListBinding mBinding;
    private ProductsListViewModel mProductsListViewModel;
    private ProductListAdapter mProductListAdapter;

    public static ProductsListFragment newInstance() {
        Bundle args = new Bundle();
        ProductsListFragment fragment = new ProductsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_list, container, false);
        mProductsListViewModel = ViewModelProviders.of(this).get(ProductsListViewModel.class);
        mProductsListViewModel.setProductListFromApi();
        setRVProductsListLayout();
        setProductListAdapter();


        mBinding.filterSelectorFrame.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProductFiltersFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        mBinding.sortTypeSelectorFrame.setOnClickListener(v -> {
            SortDialogFragment sortDialogFragment = SortDialogFragment.newInstance();
            sortDialogFragment.setTargetFragment(ProductsListFragment.this, REQUEST_CODE_FOR_SORT_DIALOG);
            sortDialogFragment.show(getActivity().getSupportFragmentManager(), SORT_DIALOG_FRAGMENT);
        });
        return mBinding.getRoot();
    }


    private void setProductListAdapter() {


        mProductListAdapter = new ProductListAdapter(getActivity());
        mProductsListViewModel.getProductList().observe(this, products -> {
            mProductListAdapter.setProducts(products);
            mBinding.fragmentProductListPb.setVisibility(View.GONE);
            mBinding.fragmentProductsListRv.setVisibility(View.VISIBLE);

        });

        mBinding.fragmentProductsListRv.setAdapter(mProductListAdapter);
    }

    private void setRVProductsListLayout() {

        mBinding.fragmentProductsListRv.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                1,
                GridLayoutManager.VERTICAL,
                false
        );
        mBinding.fragmentProductsListRv.setLayoutManager(gridLayoutManager);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);
        mBinding.fragmentProductsListRv.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_FOR_SORT_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    mProductsListViewModel.setProductListFromApi();
                    setProductListAdapter();
                }
            default:
                break;
        }

    }


}
