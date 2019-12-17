package com.example.myshop.network.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

public class VolleyRepository {

    private final String CONSUMER_KEY = "ck_f025265e3479f7bee8e93bffe5685517b93ec27d";
    private final String CONSUMER_SECRET = "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0";

    private Uri baseUrl = Uri.parse("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .buildUpon()
            .appendQueryParameter("consumer_key", CONSUMER_KEY)
            .appendQueryParameter("consumer_secret", CONSUMER_SECRET)
            .build();



    private static VolleyRepository mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;



    private VolleyRepository(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = getImageLoader();
    }


    public static synchronized VolleyRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyRepository(context);
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

    public String getProductsSearchUrl(String search) {
        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .appendQueryParameter("search", search)
                .build();
        return url.toString();
    }

    public String getCategories(String perPage){
        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .appendPath("categories")
                .appendQueryParameter("per_page",perPage)
                .build();
        return url.toString();
    }

    public String getProductWithCategory(String categoryId){
        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .appendQueryParameter("category",categoryId)
                .build();
        return url.toString();
    }

    public String getProductsBasket(){
        Uri url = baseUrl
                .buildUpon()
                .appendPath("products")
                .build();
        return url.toString();
    }
}
