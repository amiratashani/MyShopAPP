package com.example.myshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;
import com.example.myshop.view.activity.MainActivity;
import com.example.myshop.view.fragment.ProductsListFragment;
import com.example.myshop.databinding.ItemRvSubCategorisHolderBinding;
import com.example.myshop.model.category.Category;
import com.example.myshop.viewmodel.CategoryViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Category> mListSubCategory;

    public void setListSubCategory(List<Category> listSubCategory) {
        mListSubCategory = listSubCategory;
        notifyDataSetChanged();
    }

    public SubCategoriesAdapter(Context context, Fragment fragment) {
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvSubCategorisHolderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rv_sub_categoris_holder, parent, false);

        return new SubCategoriesHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SubCategoriesHolder subCategoriesHolder = (SubCategoriesHolder) holder;
        subCategoriesHolder.bind(mListSubCategory.get(position));
    }

    @Override
    public int getItemCount() {
        return mListSubCategory.size();
    }


    private class SubCategoriesHolder extends RecyclerView.ViewHolder {
        private Category mSubCategory;
        private ItemRvSubCategorisHolderBinding mBinding;
        private CategoryViewModel mCategoryViewModel;

        public SubCategoriesHolder(@NonNull ItemRvSubCategorisHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mCategoryViewModel = ViewModelProviders.of((MainActivity) mContext).get(CategoryViewModel.class);
            mBinding.setCategoryViewModel(mCategoryViewModel);
            binding.getRoot().setOnClickListener(v -> {
                mCategoryViewModel.setCategory(mSubCategory);
                mCategoryViewModel.generateUrlRequest();
                ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProductsListFragment.newInstance())
                        .addToBackStack("productListFragment")
                        .commit();
            });

        }

        private void bind(Category subCategory) {
            mSubCategory = subCategory;
            mBinding.getCategoryViewModel().setCategory(subCategory);
            mBinding.executePendingBindings();
            setImageSubCategory();
        }

        private void setImageSubCategory() {

            String url;
            if (mCategoryViewModel.getCategory().getValue().getImage() != null) {
                url = mCategoryViewModel.getCategory().getValue().getImage().getSrc();
                Picasso.get().load(url).into(mBinding.imageViewSubCategoryImage);
            } else {
                Picasso.get().load(R.drawable.alt).into(mBinding.imageViewSubCategoryImage);
            }
        }

    }
}
