package com.example.myshop.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myshop.R;
import com.example.myshop.model.attributes.Attribute;
import com.example.myshop.model.attributes.AttributeTerm;
import com.example.myshop.repositories.FilterRepository;
import com.example.myshop.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;

public class ProductFilterViewModel extends AndroidViewModel {

    private FilterRepository mRepository;
    private MutableLiveData<List<Attribute>> mAttributes;

    private MutableLiveData<Integer> mTypeAdapter;
    private MutableLiveData<Integer> mAttributeId;

    private MutableLiveData<HashMap<Integer, Boolean>> mColorFilter;
    private MutableLiveData<HashMap<Integer, Boolean>> mSizeFilter;

    private MutableLiveData<String> mMinPrice;
    private MutableLiveData<String> mMaxPrice;

    private MutableLiveData<String> mUrlRequestFilter;
    private MutableLiveData<String> mUrlFilter;

    private MutableLiveData<Integer> mCheckRadioButton;

    public LiveData<List<Attribute>> getAttributes() {
        return mAttributes;
    }

    public LiveData<Integer> getTypeAdapter() {
        return mTypeAdapter;
    }

    public LiveData<Integer> getAttributeId() {
        return mAttributeId;
    }

    public LiveData<String> getMinPrice() {
        return mMinPrice;
    }

    public LiveData<String> getMaxPrice() {
        return mMaxPrice;
    }

    public LiveData<Integer> getCheckRadioButton() {
        return mCheckRadioButton;
    }

    public ProductFilterViewModel(@NonNull Application application) {
        super(application);
        mRepository = FilterRepository.getInstance(getApplication());
        mAttributes = mRepository.getAttributes();

        mTypeAdapter = new MutableLiveData<>();
        mAttributeId = new MutableLiveData<>();

        mColorFilter = mRepository.getColorFilter();
        mSizeFilter = mRepository.getSizeFilter();

        mMinPrice = new MutableLiveData<>();
        mMinPrice.setValue("");
        mMaxPrice = new MutableLiveData<>();
        mMaxPrice.setValue("");

        mUrlRequestFilter = mRepository.getUrlRequestFilter();
        mUrlFilter = new MutableLiveData<>();

        mCheckRadioButton = new MutableLiveData<>(R.id.newest);
    }

    public void setAttributes() {
        mRepository.setAttributesFromApi();
        mRepository.setAttributesTermsFromApi();
    }

    public void setTypeAdapter(int typeAdapter) {
        mTypeAdapter.setValue(typeAdapter);
    }

    public void setAttributeId(int attributeId) {
        mAttributeId.setValue(attributeId);
    }


    public List<AttributeTerm> getAttributeTerms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return mAttributes.getValue().stream()
                    .filter(attribute -> attribute.getId() == mAttributeId.getValue())
                    .findFirst()
                    .orElse(null).getAttributeTerms();
        }
        return null;
    }


    public void setColorFilter() {
        mRepository.setColorFilter();
    }

    public void setSizeFilter() {
        mRepository.setSizeFilter();
    }


    public boolean getIsCheck(int attributeTermId) {
        if (mAttributeId.getValue() == 3) {
            return mColorFilter.getValue().get(attributeTermId);
        } else {
            return mSizeFilter.getValue().get(attributeTermId);
        }
    }

    public void setIsCheck(int attributeTermId, boolean isCheck) {
        if (mAttributeId.getValue() == 3) {
            if (isCheck)
                mRepository.emptySizeFilter();
            HashMap<Integer, Boolean> hashMap = mColorFilter.getValue();
            hashMap.put(attributeTermId, isCheck);
            mColorFilter.setValue(hashMap);
        }
        if (mAttributeId.getValue() == 4) {
            if (isCheck)
                mRepository.emptyColorFilter();
            HashMap<Integer, Boolean> hashMap = mSizeFilter.getValue();
            hashMap.put(attributeTermId, isCheck);
            mSizeFilter.setValue(hashMap);
        }
    }

    public void setMinPrice(String minPrice) {
        mMinPrice.setValue(minPrice);
    }

    public void setMaxPrice(String maxPrice) {
        mMaxPrice.setValue(maxPrice);
    }

    public void urlFilter() {
        StringBuilder url = new StringBuilder();
        StringBuilder colorFilter = new StringBuilder();
        StringBuilder sizeFilter = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mColorFilter.getValue().forEach((integer, aBoolean) -> {
                if (aBoolean) {
                    colorFilter.append(",").append(integer);
                }
            });
            mSizeFilter.getValue().forEach((integer, aBoolean) -> {
                if (aBoolean) {
                    sizeFilter.append(",").append(integer);
                }
            });
        }

        if (sizeFilter.length() != 0) {
            url.append("&attribute=pa_size").append("&attribute_term=").append(sizeFilter);
        }
        if (colorFilter.length() != 0) {
            url.append("&attribute=pa_color").append("&attribute_term=").append(colorFilter);
        }

        url.append("&min_price=").append(mMinPrice.getValue()).append("&max_price=").append(mMinPrice.getValue());


        mUrlFilter.setValue(url.toString());

        setUrlRequestFilter(mUrlRequestFilter.getValue() + mUrlFilter.getValue());
    }

    public void sortFilter(int sortId) {
        StringBuilder url = new StringBuilder();

        switch (sortId) {
            case R.id.popularity:
                url.append("&orderby=popularity");
                break;
            case R.id.rating:
                url.append("&orderby=rating");
                break;
            case R.id.newest:
                url.append("&orderby=date");
                break;
            case R.id.price_ascending:
                url.append("&orderby=price").append("&order=asc");
                break;
            case R.id.price_descending:
                url.append("&orderby=price").append("&order=desc");
                break;
        }
        mCheckRadioButton.setValue(sortId);
        mUrlFilter.setValue(url.toString());
        setUrlRequestFilter(mUrlRequestFilter.getValue() + mUrlFilter.getValue());

    }

    public void setUrlRequestFilter(String urlRequestFilter) {
        ProductRepository.getInstance(getApplication()).setUrlRequest(urlRequestFilter);
    }


}
