package com.example.myshop.network.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

public class MyVolley {

    private final String CONSUMER_KEY = "ck_f02g5265ssse3479f7bee8e93bffe5685517b93ec27d";
    private final String CONSUMER_SECRET = "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0";

    private Uri baseUrl = Uri.parse("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .buildUpon()
            .appendQueryParameter("consumer_key", CONSUMER_KEY)
            .appendQueryParameter("consumer_secret", CONSUMER_SECRET)
            .build();



    private static MyVolley mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;



    private MyVolley(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = getImageLoader();
    }


    public static MyVolley getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyVolley(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = com.android.volley.toolbox.Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> mLruCache = new LruCache<>(20);

                @Override
                public Bitmap getBitmap(String url) {
                    return mLruCache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    mLruCache.put(url, bitmap);
                }
            });
        }
        return mImageLoader;
    }

    public String getProductUrl(String id) {

        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .appendPath(id)
                .build();
        return url.toString();
    }

    public String getProductsUrl(String order) {
        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .appendQueryParameter("orderby", order)
                .build();
        return url.toString();
    }

    public String getCategories(){

        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .appendPath("categories")
                .build();
        return url.toString();
    }
}
