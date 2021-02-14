package com.example.myshop.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.myshop.model.category.Category;
import java.util.List;

public class CategoryRepository {

    private static CategoryRepository mInstance;

    private MutableLiveData<List<Category>> mParentCategories;
    private MutableLiveData<List<Category>> mSubCategories;

    public static CategoryRepository getInstance() {
        if (mInstance == null) {
            mInstance = new CategoryRepository();
        }
        return mInstance;
    }

    private CategoryRepository() {
        mParentCategories = new MutableLiveData<>();
        mSubCategories=new MutableLiveData<>();
    }

    public MutableLiveData<List<Category>> getParentCategories() {
        return mParentCategories;
    }

    public MutableLiveData<List<Category>> getSubCategories() {
        return mSubCategories;
    }

}
