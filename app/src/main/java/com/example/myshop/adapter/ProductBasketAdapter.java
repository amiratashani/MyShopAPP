package com.example.myshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;
import com.example.myshop.databinding.ItemRvProductBasketHolderBinding;
import com.example.myshop.model.product.Product;
import com.example.myshop.view.activity.MainActivity;
import com.example.myshop.view.fragment.ProductBasketFragment;
import com.example.myshop.view.fragment.ProductDetailsFragment;
import com.example.myshop.viewmodel.ProductBasketViewModel;
import com.example.myshop.viewmodel.ProductDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductBasketAdapter extends RecyclerView.Adapter {

    private List<Product> mProductsBasket;
    private Context mContext;

    public ProductBasketAdapter(Context context) {
        mContext = context;
    }

    public void setProductsBasket(List<Product> productsBasket) {
        mProductsBasket = productsBasket;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvProductBasketHolderBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rv_product_basket_holder, parent, false);
        return new ProductBasketHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductBasketHolder productBasketHolder = (ProductBasketHolder) holder;
        productBasketHolder.bind(mProductsBasket.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductsBasket.size();
    }

    private class ProductBasketHolder extends RecyclerView.ViewHolder {
        private ProductDetailsViewModel mProductDetailsViewModel;
        private ProductBasketViewModel mProductBasketViewModel;
        private ItemRvProductBasketHolderBinding mBinding;
        private Product mProduct;

        public ProductBasketHolder(@NonNull ItemRvProductBasketHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mProductDetailsViewModel = ViewModelProviders.of((MainActivity) mContext).get(ProductDetailsViewModel.class);
            mProductBasketViewModel = ViewModelProviders.of((MainActivity) mContext).get(ProductBasketViewModel.class);

            mBinding.holderTvProductName.setOnClickListener(v -> {
                mProductDetailsViewModel.setProduct(mProduct);
                ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailsFragment.newInstance())
                        .addToBackStack("productDetailFragment")
                        .commit();
            });

            mBinding.cDelete.setOnClickListener(v -> {
                mProductBasketViewModel.deleteFromBasket(mProduct.getId());
            });
        }

        public void bind(Product product) {
            mProduct = product;
            mProductDetailsViewModel.setProduct(product);
            mBinding.setProductDetailsViewModel(mProductDetailsViewModel);
            mBinding.executePendingBindings();
            setImageView();
            setCOUNTRIES();
        }

        private void setImageView() {
            String url;
            if (mProductDetailsViewModel.getProduct().getValue().getImages().size() != 0) {
                url = mProductDetailsViewModel.getProduct().getValue().getImages().get(0).getSrc();
                Picasso.get().load(url).placeholder(R.drawable.alt).into(mBinding.holderIvProductImage);
            } else {
                Picasso.get().load(R.drawable.alt).into(mBinding.holderIvProductImage);
            }
        }

        public void setCOUNTRIES() {
            Integer[] COUNTRIES = new Integer[]{1, 2, 3, 4};
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(mContext, R.layout.dropdown_menu_popup_item, COUNTRIES);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mBinding.cCount.setAdapter(adapter);

            mBinding.cCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    double item = Double.valueOf(parent.getItemAtPosition(position).toString());
                    double price=Double.valueOf(mProduct.getPrice());
                    mBinding.holderTvFinalProductPrice.setText(String.valueOf(item*price));
                    mProductBasketViewModel.updateCount(mProduct.getId(),(int) item);
                    mProductBasketViewModel.updateTotalPrice();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
