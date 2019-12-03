package com.example.myshop.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshop.R;
import com.example.myshop.controller.adapter.SubCategoriesAdapter;
import com.example.myshop.controller.repository.MyShopRepository;
import com.example.myshop.model.category.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoriesFragment extends Fragment {

    private static final String ARG_CATEGORY_PARENT_ID = "categoryParentId";
    private int parentCategory;
    private SubCategoriesAdapter mSubCategoriesAdapter;

    private RecyclerView mRVSubCategories;


    public static SubCategoriesFragment newInstance(int parentId) {

        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_PARENT_ID, parentId);

        SubCategoriesFragment fragment = new SubCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public SubCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentCategory = getArguments().getInt(ARG_CATEGORY_PARENT_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_categories, container, false);

        initUi(view);
        setSubCategoriesAdapter();

        return view;
    }

    private void initUi(View view) {
        mRVSubCategories = view.findViewById(R.id.fragment_sub_categories_recycler_view);
        mRVSubCategories.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        mRVSubCategories.setLayoutManager(gridLayoutManager);
    }

    private void setSubCategoriesAdapter() {

        Category subCategory;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            subCategory = MyShopRepository.getInstance(getActivity()).getParentCategory()
                    .stream()
                    .filter(category -> category.getId() == parentCategory)
                    .findAny()
                    .orElse(null);
        } else
            subCategory = null;

        if (mSubCategoriesAdapter == null) {
            mSubCategoriesAdapter = new SubCategoriesAdapter(getActivity(), subCategory.getSubCategory());
            mRVSubCategories.setAdapter(mSubCategoriesAdapter);
        } else {
            mSubCategoriesAdapter.setListSubCategory(subCategory.getSubCategory());
            mSubCategoriesAdapter.notifyDataSetChanged();
        }
    }

}
