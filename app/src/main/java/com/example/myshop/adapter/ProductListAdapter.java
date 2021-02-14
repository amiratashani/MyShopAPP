package com.example.myshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;
import com.example.myshop.databinding.ItemRvProductListHolderBinding;
import com.example.myshop.model.product.Product;
import com.example.myshop.view.activity.MainActivity;
import com.example.myshop.view.fragment.ProductDetailsFragment;
import com.example.myshop.viewmodel.ProductDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter {

    private List<Product> mProducts;
    private Context mContext;

    public ProductListAdapter(Context context) {
        mContext = context;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvProductListHolderBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rv_product_list_holder, parent, false);
        return new ProductListHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductListHolder productListHolder = (ProductListHolder) holder;
        productListHolder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    private class ProductListHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ProductDetailsViewModel mProductDetailsViewModel;
        private ItemRvProductListHolderBinding mBinding;

        public ProductListHolder(@NonNull ItemRvProductListHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mProductDetailsViewModel = ViewModelProviders.of((MainActivity) mContext).get(ProductDetailsViewModel.class);

            itemView.setOnClickListener(v -> {
                mBinding.getProductDetailsViewModel().setProduct(mProduct);
                ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailsFragment.newInstance())
                        .addToBackStack("productDetailFragment")
                        .commit();
            });
        }

        private void bind(Product product) {
            mProduct = product;
            mProductDetailsViewModel.setProduct(product);
            mBinding.setProductDetailsViewModel(mProductDetailsViewModel);
            mBinding.executePendingBindings();
            setNetworkImageView();
        }

        private void setNetworkImageView() {
            String url;
            if (mProduct.getImages().size() != 0) {
                url = mProduct.getImages().get(0).getSrc();
                Picasso.get().load(url).placeholder(R.drawable.alt).into(mBinding.holderIvProductImage);
            } else {
                Picasso.get().load(R.drawable.alt).into(mBinding.holderIvProductImage);
            }
        }
    }
}
