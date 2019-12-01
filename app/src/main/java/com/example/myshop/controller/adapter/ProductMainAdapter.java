package com.example.myshop.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.myshop.R;
import com.example.myshop.controller.activity.MainActivity;
import com.example.myshop.controller.fragment.DetailsProductFragment;
import com.example.myshop.model.product.Product;
import com.example.myshop.network.volley.MyVolley;

import java.util.List;

public class ProductMainAdapter extends RecyclerView.Adapter {

    private List<Product> mProducts;
    private Context mContext;

    public ProductMainAdapter(List<Product> products, Context context) {
        mProducts = products;
        mContext = context;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main_holder, parent, false);
        return new ProductMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductMainViewHolder productViewHolder = (ProductMainViewHolder) holder;
        productViewHolder.bind(mProducts.get(position));

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductMainViewHolder extends RecyclerView.ViewHolder {
        private Product mProduct;
        private NetworkImageView mNetworkImageViewProduct;
        private TextView mTextViewName;
        private TextView mTextViewPrice;

        public ProductMainViewHolder(@NonNull View itemView) {
            super(itemView);
            mNetworkImageViewProduct = itemView.findViewById(R.id.product_image_network_iv);
            mTextViewName = itemView.findViewById(R.id.product_name_tv);
            mTextViewPrice = itemView.findViewById(R.id.product_price_tv);

            itemView.setOnClickListener(v -> {
                ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, DetailsProductFragment.newInstance(String.valueOf(mProduct.getId())))
                        .addToBackStack(null)
                        .commit();
            });
        }

        private void bind(Product product) {
            mProduct = product;
            setNetworkImageView();
            mTextViewName.setText(product.getName());
            mTextViewPrice.setText(product.getPrice());
        }

        private void setNetworkImageView() {
            mNetworkImageViewProduct.setImageUrl(mProduct.getImages().get(0).getSrc(),
                    MyVolley.getInstance(mContext).getImageLoader());
        }

    }


}
