package com.example.myshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.myshop.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageSliderAdapter extends SliderViewAdapter {

    private Context mContext;
    private List<String> mListUrl;


    public ImageSliderAdapter(Context context, List<String> listUrl) {
        mContext = context;
        mListUrl = listUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider_holder, null, false);
        return new SliderMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        SliderMainViewHolder sliderMainViewHolder = (SliderMainViewHolder) viewHolder;

        sliderMainViewHolder.bind(mListUrl.get(position));

    }

    @Override
    public int getCount() {
        return mListUrl.size();
    }

    public class SliderMainViewHolder extends SliderViewAdapter.ViewHolder {

        ImageView imageViewBackground;


        public SliderMainViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
        }

        private void bind(String url) {
            Picasso.get()
                    .load(url)
                    .fit()
                    .into(imageViewBackground);
        }
    }
}
