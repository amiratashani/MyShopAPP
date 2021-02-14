package com.example.myshop.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyShopPreferences {

    private static final String PREF_NAME = "myShopPreferences";
    private static final String PRODUCTS_BASKET = "productsBasket";
    private static final String LAST_PRODUCT_ID="lastProductId";


    private static SharedPreferences getMyShopSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static ArrayList<String> getProductsBasket(Context context) {
        SharedPreferences sharedPreferences = getMyShopSharedPreferences(context);
        if (sharedPreferences==null)
            return null;
        Gson gson = new Gson();
        String json= sharedPreferences.getString(PRODUCTS_BASKET,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void setProductsBasket(Context context,ArrayList<String> productBasket) {
        SharedPreferences sharedPreferences = getMyShopSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(productBasket);
        editor.putString(PRODUCTS_BASKET,json);
        editor.apply();
    }





    public static String getLastProductId(Context context){
        SharedPreferences sharedPreferences = getMyShopSharedPreferences(context);
        if (sharedPreferences==null)
            return null;
        return sharedPreferences.getString(LAST_PRODUCT_ID,null);
    }

    public static void setLastProductId(Context context,String productLastId) {
        SharedPreferences sharedPreferences = getMyShopSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(LAST_PRODUCT_ID,productLastId);
        editor.apply();
    }

}
