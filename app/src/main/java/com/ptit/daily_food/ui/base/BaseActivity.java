package com.ptit.daily_food.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ptit.daily_food.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected int layoutRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        setContentView(layoutRes);
        initView();
        initPresenter();
    }

    protected abstract void initLayout();

    protected abstract void initPresenter();

    protected abstract void initView();

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContent, fragment)
                .commit();
    }
}
