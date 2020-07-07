package com.ptit.daily_food.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.ui.base.BaseActivity;
import com.ptit.daily_food.ui.fragment.collection.CollectionFragment;
import com.ptit.daily_food.ui.fragment.cooking.CookingFragment;
import com.ptit.daily_food.ui.fragment.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static String EXTRA_FOOD_LIST =
            "com.ptit.daily_food.ui.activity.main.KEY_FOOD_LIST";

    private BottomNavigationView bottomNavigationView;

    private List<FoodDetail> allFoods = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelected =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home: {
                            setFragment(HomeFragment.newInstance(allFoods));
                            return true;
                        }
                        case R.id.navigation_collection: {
                            setFragment(CollectionFragment.newInstance());
                            return true;
                        }
                        case R.id.navigation_cooking: {
                            setFragment(CookingFragment.newInstance());
                            return true;
                        }
                        default: {
                            return false;
                        }
                    }
                }
            };

    public static Intent getIntent(Context context, List<FoodDetail> foodDetails) {
        return new Intent(context, MainActivity.class)
                .putParcelableArrayListExtra(EXTRA_FOOD_LIST, new ArrayList(foodDetails));
    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        mapping();
        createFoodsAll();
        setFragment(HomeFragment.newInstance(allFoods));
        initBottomNavigation();
    }

    private void mapping() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    private void initBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavSelected);
    }

    private void createFoodsAll() {
        Intent intent = getIntent();
        ArrayList<FoodDetail> extras = intent.getParcelableArrayListExtra(EXTRA_FOOD_LIST);
        if (extras != null) {
            allFoods.addAll(extras);
        }
    }
}
