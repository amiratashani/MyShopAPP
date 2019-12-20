package com.example.myshop.adapter.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;
import com.example.myshop.databinding.ItemRvFilterTitleHolderBinding;
import com.example.myshop.model.attributes.Attribute;
import com.example.myshop.view.activity.MainActivity;
import com.example.myshop.viewmodel.ProductFilterViewModel;


import java.util.List;

public class FilterTitlesAdapter extends RecyclerView.Adapter<FilterTitlesAdapter.FilterTitleViewHolder> {

    //  private List<String> mFilterTitles = new ArrayList<>(Arrays.asList("رنگ", "سایز", "محدوده قیمت"));
    private List<Attribute> mAttributes;
    private Context mContext;
    private int selectedItemPosition = 0;

    public FilterTitlesAdapter(Context context) {
        mContext = context;
    }

    public void setAttributes(List<Attribute> attributes) {
        mAttributes = attributes;
        if (attributes.size() < 3)
            mAttributes.add(new Attribute(0, "Price range", "pa_price"));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilterTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvFilterTitleHolderBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rv_filter_title_holder, parent, false);
        return new FilterTitleViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterTitleViewHolder holder, int position) {
        holder.bind(mAttributes.get(position));
    }

    @Override
    public int getItemCount() {
        return mAttributes.size();
    }

    class FilterTitleViewHolder extends RecyclerView.ViewHolder {
        private ItemRvFilterTitleHolderBinding mBinding;
        private ProductFilterViewModel mProductFilterViewModel;
        private Attribute mAttribute;

        FilterTitleViewHolder(@NonNull ItemRvFilterTitleHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mProductFilterViewModel = ViewModelProviders.of((MainActivity) mContext).get(ProductFilterViewModel.class);
            mBinding.getRoot().setOnClickListener(v -> {
                selectedItemPosition = getAdapterPosition();
                notifyDataSetChanged();
                if (mAttribute.getId() == 0) {
                    mProductFilterViewModel.setTypeAdapter(2);
                } else {
                    mProductFilterViewModel.setTypeAdapter(1);
                    mProductFilterViewModel.setAttributeId(mAttribute.getId());
                }
            });
        }

        void bind(Attribute attribute) {
            selectedItem();
            mAttribute = attribute;
            mBinding.itemTitle.setText(attribute.getName());
            mBinding.executePendingBindings();
        }

        private void selectedItem() {
            if (selectedItemPosition == getAdapterPosition()) {
                mBinding.itemTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black_light));
                mBinding.getRoot().setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_pure));

            } else {
                mBinding.itemTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white_pure));
                mBinding.getRoot().setBackgroundColor(ContextCompat.getColor(mContext, R.color.black_light));
            }
        }


    }
}
