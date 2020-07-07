package com.ptit.daily_food.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ptit.daily_food.R;

public abstract class BaseFragment extends Fragment {

    protected int layoutRes;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        getArgument();
        initLayout();
        return inflater.inflate(layoutRes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initActionBar();
        initPresenter();
    }

    protected abstract void initView();

    protected abstract void initLayout();

    protected abstract void initPresenter();

    protected abstract void initActionBar();

    protected abstract void getArgument();

    public void switchByAddFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameContent, fragment)
                    .commit();
        }
    }

    public void removeFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }
}
