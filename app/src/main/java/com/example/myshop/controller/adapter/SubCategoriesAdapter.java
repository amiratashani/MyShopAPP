package com.example.myshop.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;
import com.example.myshop.model.category.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Category> mListSubCategory;

    public void setListSubCategory(List<Category> listSubCategory) {
        mListSubCategory = listSubCategory;
    }

    public SubCategoriesAdapter(Context context, List<Category> listSubCategory) {
        mContext = context;
        mListSubCategory = listSubCategory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_categoris_sub_holder, parent, false);

        return new SubCategoriesHolder(view);
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
        private TextView mTVSubCategoryTitle;
        private ImageView mIVSubCategoryImage;

        public SubCategoriesHolder(@NonNull View itemView) {
            super(itemView);

            mTVSubCategoryTitle = itemView.findViewById(R.id.text_view_sub_category_title);
            mIVSubCategoryImage = itemView.findViewById(R.id.image_view_sub_category_image);

        }

        private void bind(Category subCategory) {
            mSubCategory = subCategory;
            setImageSubCategory();
            mTVSubCategoryTitle.setText(subCategory.getName());

        }

        private void setImageSubCategory() {

            String url;
            if (mSubCategory.getImage() != null) {
                url = mSubCategory.getImage().getSrc();
                Picasso.get().load(url).into(mIVSubCategoryImage);
            } else {
                Picasso.get().load(R.drawable.alt).into(mIVSubCategoryImage);
            }
        }

    }
}
