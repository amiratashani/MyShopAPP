package com.example.myshop.repositories;

import android.content.Context;
import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myshop.model.attributes.Attribute;
import com.example.myshop.model.attributes.AttributeTerm;

import com.example.myshop.network.volley.VolleyRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FilterRepository {
    private static FilterRepository sInstance;
    private Context mContext;
    private MutableLiveData<List<Attribute>> mAttributes;
    private MutableLiveData<HashMap<Integer, Boolean>> mColorFilter;
    private MutableLiveData<HashMap<Integer, Boolean>> mSizeFilter;
    private MutableLiveData<String> mUrlRequestFilter;

    public static FilterRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FilterRepository(context);
        }
        return sInstance;
    }

    public MutableLiveData<List<Attribute>> getAttributes() {
        return mAttributes;
    }

    public MutableLiveData<HashMap<Integer, Boolean>> getColorFilter() {
        return mColorFilter;
    }

    public MutableLiveData<HashMap<Integer, Boolean>> getSizeFilter() {
        return mSizeFilter;
    }

    public MutableLiveData<String> getUrlRequestFilter() {
        return mUrlRequestFilter;
    }

    private FilterRepository(Context context) {
        mContext = context;
        mAttributes = new MutableLiveData<>();
        mColorFilter = new MutableLiveData<>();
        mSizeFilter = new MutableLiveData<>();
        mUrlRequestFilter =new MutableLiveData<>();
    }

    public void setAttributesFromApi() {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(3, "Color", "pa_color"));
        attributes.add(new Attribute(4, "Size", "pa_size"));
        mAttributes.setValue(attributes);
    }

    public void setAttributesTermsFromApi() {
        List<Attribute> attributes = new ArrayList<>();

        for (Attribute attribute : mAttributes.getValue()) {
            String url = VolleyRepository.getInstance(mContext).getAttributeTermUrl(String.valueOf(attribute.getId()));
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        List<AttributeTerm> attributeTerms = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<AttributeTerm>>() {
                        }.getType());
                        attribute.setAttributeTerms(attributeTerms);
                        attributes.add(attribute);
                        mAttributes.setValue(attributes);
                    },
                    error -> {
                    });
            VolleyRepository.getInstance(mContext).addToRequestQueue(request);
        }
    }

    public void setColorFilter() {
        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAttributes.getValue().stream()
                    .filter(attribute -> attribute.getId() == 3)
                    .findFirst()
                    .orElse(null).getAttributeTerms().stream()
                    .forEach(attributeTerm -> hashMap.put(attributeTerm.getId(), false));
        }
        mColorFilter.setValue(hashMap);
    }

    public void setSizeFilter() {
        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAttributes.getValue().stream()
                    .filter(attribute -> attribute.getId() == 4)
                    .findFirst()
                    .orElse(null).getAttributeTerms().stream()
                    .forEach(attributeTerm -> hashMap.put(attributeTerm.getId(), false));
        }
        mSizeFilter.setValue(hashMap);
    }

    public void emptySizeFilter() {
        HashMap<Integer, Boolean> hashMap = mSizeFilter.getValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAttributes.getValue().stream()
                    .filter(attribute -> attribute.getId() == 4)
                    .findFirst()
                    .orElse(null).getAttributeTerms().stream()
                    .forEach(attributeTerm -> hashMap.put(attributeTerm.getId(), false));
        }
        mSizeFilter.setValue(hashMap);
    }


    public void emptyColorFilter() {
        HashMap<Integer, Boolean> hashMap = mColorFilter.getValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAttributes.getValue().stream()
                    .filter(attribute -> attribute.getId() == 3)
                    .findFirst()
                    .orElse(null).getAttributeTerms().stream()
                    .forEach(attributeTerm -> hashMap.put(attributeTerm.getId(), false));
        }
        mColorFilter.setValue(hashMap);
    }

    public void setUrlRequestFilter(String urlRequestFilter) {
        mUrlRequestFilter.setValue(urlRequestFilter);
    }
}
