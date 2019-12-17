package com.example.myshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myshop.R;

import java.util.List;

public class ColorDetailAdapter extends RecyclerView.Adapter {

    private List<String> mColors;
    private Context mContext;

    public ColorDetailAdapter(List<String> colors, Context context) {
        mColors = colors;
        mContext = context;
    }

    public void setColors(List<String> colors) {
        mColors = colors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_config_color_holder, parent, false);
        return new ConfigColorDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConfigColorDetailHolder configColorDetailHolder = (ConfigColorDetailHolder) holder;
        configColorDetailHolder.bind(mColors.get(position));
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }


    private class ConfigColorDetailHolder extends RecyclerView.ViewHolder {

        private String mColor;
        private ImageView mIMColorImage;
        private TextView mTVColorTitle;
        private RelativeLayout mRelativeLayout;


        private Drawable mDrawable;
        private GradientDrawable mShapeDrawable;

        public ConfigColorDetailHolder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.color_container);
            mIMColorImage = itemView.findViewById(R.id.color_image);
            mTVColorTitle = itemView.findViewById(R.id.color_title);
            mDrawable = AppCompatResources.getDrawable(mContext, R.drawable.circle_color);
            mShapeDrawable = (GradientDrawable) mDrawable;

            mRelativeLayout.setOnClickListener(v -> {
                if (mRelativeLayout.isSelected())
                    mRelativeLayout.setSelected(false);
                else
                    mRelativeLayout.setSelected(true);

            });
        }

        private void bind(String color) {
            mColor = color;
            mTVColorTitle.setText(color);
            setColor();
            mIMColorImage.setImageDrawable(mDrawable);
        }

        private void setColor() {
            switch (mColor) {
                case "White":
                    mShapeDrawable.setColor(Color.WHITE);
                    break;
                case "Black":
                    mShapeDrawable.setColor(Color.BLACK);
                    break;
                case "Red":
                    mShapeDrawable.setColor(Color.RED);
                    break;
                case "Yellow":
                    mShapeDrawable.setColor(Color.YELLOW);
                    break;
                case "Blue":
                    mShapeDrawable.setColor(Color.BLUE);
                    break;
                case "Gray":
                    mShapeDrawable.setColor(Color.GRAY);
                    break;
            }

        }

    }

}
