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
import com.example.myshop.controller.activity.MainActivity;
import com.example.myshop.controller.fragment.ProductDetailsFragment;
import com.example.myshop.model.product.Product;

import com.example.myshop.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter {

    private List<Product> mProducts;
    private Context mContext;
    private Type mType;

    public ProductListAdapter(List<Product> products, Context context, Type type) {
        mProducts = products;
        mContext = context;
        mType = type;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == Type.IN_MAIN.getAngle()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_product_list_main_holder, parent, false);
            return new ProductListViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_product_list_holder, parent, false);
            return new ProductListViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductListViewHolder productViewHolder = (ProductListViewHolder) holder;
        productViewHolder.bind(mProducts.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        switch (mType) {
            case IN_LIST:
                return Type.IN_LIST.getAngle();

            case IN_MAIN:
                return Type.IN_MAIN.getAngle();

            default:
                return 5;
        }

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    private class ProductListViewHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private ImageView mImageViewImage;
        private TextView mTextViewName;
        private TextView mTextViewPrice;

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewImage = itemView.findViewById(R.id.holder_iv_productImage);
            mTextViewName = itemView.findViewById(R.id.holder_tv_productName);
            mTextViewPrice = itemView.findViewById(R.id.holder_tv_productPrice);

            itemView.setOnClickListener(v -> {
                ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProductDetailsFragment.newInstance(String.valueOf(mProduct.getId())))
                        .addToBackStack(null)
                        .commit();
            });
        }

        private void bind(Product product) {
            mProduct = product;
            setNetworkImageView();
            mTextViewName.setText(product.getName());
            mTextViewPrice.setText(Utility.addCommaInToNumber().format(Integer.valueOf(product.getPrice())));
        }

        private void setNetworkImageView() {
            String url;
            if (mProduct.getImages().size() != 0) {
                url = mProduct.getImages().get(0).getSrc();
                Picasso.get().load(url).placeholder(R.drawable.alt).into(mImageViewImage);
            } else {
                Picasso.get().load(R.drawable.alt).into(mImageViewImage);

            }

        }

    }

    public enum Type {
        IN_MAIN(0), IN_LIST(1);

        private int angle;

        Type(int angle) {
            this.angle = angle;
        }

        public int getAngle() {
            return angle;
        }

    }


}
