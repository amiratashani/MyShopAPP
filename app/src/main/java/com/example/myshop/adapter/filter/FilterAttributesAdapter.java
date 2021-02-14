package com.example.myshop.adapter.filter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;
import com.example.myshop.databinding.ItemRvFilterAttributePriceHolderBinding;
import com.example.myshop.databinding.ItemRvFilterAttributeSimpleHolderBinding;
import com.example.myshop.model.attributes.AttributeTerm;
import com.example.myshop.view.activity.MainActivity;
import com.example.myshop.viewmodel.ProductFilterViewModel;

import java.util.List;


public class FilterAttributesAdapter extends RecyclerView.Adapter {

    private static final int TYPE_SIMPLE = 1;
    private static final int TYPE_PRICE = 2;
    private Context mContext;
    private int mType;
    private List<AttributeTerm> mAttributeTerms;
    private ProductFilterViewModel mProductFilterViewModel;

    public FilterAttributesAdapter(Context context,ProductFilterViewModel productFilterViewModel) {
        mContext = context;
        mProductFilterViewModel=productFilterViewModel;
    }

    public void setAttributeTerms(List<AttributeTerm> attributeTerms) {
        mAttributeTerms = attributeTerms;
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.mType = type;
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SIMPLE: {
                ItemRvFilterAttributeSimpleHolderBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                        , R.layout.item_rv_filter_attribute_simple_holder, parent, false);
                return new SimpleFilterAttributeViewHolder(mBinding);
            }
            case TYPE_PRICE: {
                ItemRvFilterAttributePriceHolderBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                        , R.layout.item_rv_filter_attribute_price_holder, parent, false);
                return new PriceRangeViewHolder(mBinding);
            }
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_SIMPLE:
                SimpleFilterAttributeViewHolder simpleFilterAttributeViewHolder = (SimpleFilterAttributeViewHolder) holder;
                simpleFilterAttributeViewHolder.bind(mAttributeTerms.get(position));
                break;
            case TYPE_PRICE:
                PriceRangeViewHolder priceRangeViewHolder = (PriceRangeViewHolder) holder;
                priceRangeViewHolder.bind();
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mType == TYPE_PRICE)
            return 1;
        return mAttributeTerms.size();
    }


    private class SimpleFilterAttributeViewHolder extends RecyclerView.ViewHolder {
        private ItemRvFilterAttributeSimpleHolderBinding mBinding;

        private AttributeTerm mAttributeTerm;

        private SimpleFilterAttributeViewHolder(@NonNull ItemRvFilterAttributeSimpleHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.attributeCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {

                mProductFilterViewModel.setIsCheck(mAttributeTerm.getId(), isChecked);

            });
        }

        private void bind(AttributeTerm attributeTerm) {
            mAttributeTerm = attributeTerm;
            mBinding.attributeTitleTextView.setText(attributeTerm.getName());
            mBinding.attributeCheckbox.setChecked(mProductFilterViewModel.getIsCheck(attributeTerm.getId()));
            mBinding.executePendingBindings();
        }

    }


    public class PriceRangeViewHolder extends RecyclerView.ViewHolder {
        private ItemRvFilterAttributePriceHolderBinding mBinding;


        public PriceRangeViewHolder(@NonNull ItemRvFilterAttributePriceHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        private void bind() {
            mBinding.maxPrice.setText(mProductFilterViewModel.getMaxPrice().getValue());
            mBinding.minPrice.setText(mProductFilterViewModel.getMinPrice().getValue());

            mBinding.minPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mProductFilterViewModel.setMinPrice(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            mBinding.maxPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mProductFilterViewModel.setMaxPrice(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }


}
