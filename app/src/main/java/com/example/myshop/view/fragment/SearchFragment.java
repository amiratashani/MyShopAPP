package com.example.myshop.view.fragment;


import android.content.Context;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.myshop.R;
import com.example.myshop.databinding.FragmentSearchBinding;
import com.example.myshop.viewmodel.SearchViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding mBinding;
    private SearchViewModel mSearchViewModel;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        showKeyboard();

        mBinding.toolbarSearchInclude.toolbarSearchTvSearchbox.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String str = mBinding.toolbarSearchInclude.toolbarSearchTvSearchbox.getText().toString().trim();
                mSearchViewModel.setUrlRequest(str);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProductsListFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
            return false;
        });

        mBinding.toolbarSearchInclude.toolbarSearchTvSearchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 1) {
                    mBinding.toolbarSearchInclude.toolbarSearchIvClearButton.setVisibility(View.VISIBLE);
                } else {
                    mBinding.toolbarSearchInclude.toolbarSearchIvClearButton.setVisibility(View.GONE);
                }
            }
        });


        mBinding.toolbarSearchInclude.toolbarSearchIvClearButton.setOnClickListener(v -> {
            mBinding.toolbarSearchInclude.toolbarSearchTvSearchbox.setText("");
        });

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        showKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private void hideKeyboard() {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private void showKeyboard() {

        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


}
