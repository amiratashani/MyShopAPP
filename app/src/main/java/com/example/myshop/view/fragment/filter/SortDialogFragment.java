package com.example.myshop.view.fragment.filter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;

import com.example.myshop.R;
import com.example.myshop.databinding.FragmentSortDialogBinding;
import com.example.myshop.viewmodel.ProductFilterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortDialogFragment extends DialogFragment {
    private FragmentSortDialogBinding mBinding;
    private ProductFilterViewModel mProductFilterViewModel;

    public static SortDialogFragment newInstance() {

        Bundle args = new Bundle();

        SortDialogFragment fragment = new SortDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SortDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductFilterViewModel = ViewModelProviders.of(getActivity()).get(ProductFilterViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_sort_dialog, null, false);
        mBinding.radioGroup.check(mProductFilterViewModel.getCheckRadioButton().getValue());
        setRadioButtonListener();
        return new AlertDialog.Builder(getActivity())
                .setView(mBinding.getRoot())
                .create();
    }

    private void setRadioButtonListener() {
        mBinding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            mProductFilterViewModel.sortFilter(checkedId);
            Fragment fragment = getTargetFragment();
            Intent intent = new Intent();
            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            dismiss();
        });

    }

}
