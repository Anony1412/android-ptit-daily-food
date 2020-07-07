package com.ptit.daily_food.ui.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.ui.activity.food.FoodDetailActivity;
import com.ptit.daily_food.ui.adapter.FoodAdapter;
import com.ptit.daily_food.ui.base.BaseFragment;
import com.ptit.daily_food.ui.fragment.search.SearchFragment;
import com.ptit.daily_food.ui.interfaces.FoodItemClickInterface;
import com.ptit.daily_food.ultis.FoodType;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment
        extends BaseFragment
        implements View.OnClickListener,
        HomeContracts.View,
        SearchView.OnQueryTextListener,
        FoodItemClickInterface {

    public static final Integer ITEM_GRID_WIDTH = 152;
    private static final String ARGUMENT_FOOD_LIST = "ARGUMENT_FOOD_LIST";

    private TextView textActionBarTitle;
    private ImageButton buttonSearchActionBar, buttonBackActionBar, buttonCollectionActionBar;
    private Button buttonSortOtherFood;
    private RecyclerView recyclerViewDailyMenu;

    private HomePresenter homePresenter = null;
    private List<FoodDetail> otherFoods = null;
    private Boolean isSortList = false;
    private Boolean isShowFrameSearch = false;
    private RecyclerView recyclerViewOtherFood;

    public static HomeFragment newInstance(List<FoodDetail> foodDetails) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_FOOD_LIST, new ArrayList(foodDetails));
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        if (getActivity() != null) {
            textActionBarTitle = getActivity().findViewById(R.id.textActionBarTitle);
            buttonBackActionBar = getActivity().findViewById(R.id.buttonBackActionBar);
            buttonCollectionActionBar = getActivity().findViewById(R.id.buttonCollectionActionBar);
            buttonSearchActionBar = getActivity().findViewById(R.id.buttonSearchActionBar);
            buttonSortOtherFood = getActivity().findViewById(R.id.buttonSortOtherFood);
            recyclerViewDailyMenu = getActivity().findViewById(R.id.recyclerViewDailyMenu);
            recyclerViewOtherFood = getActivity().findViewById(R.id.recyclerViewOtherFood);
            searchViewHomeScreen = getActivity().findViewById(R.id.searchViewHomeScreen);
            frameSearch = getActivity().findViewById(R.id.frameSearch);
        }
    }

    @Override
    protected void initPresenter() {
        homePresenter = new HomePresenter(this);
    }

    @Override
    protected void initActionBar() {
        textActionBarTitle.setText(getString(R.string.title_home));
        buttonSearchActionBar.setOnClickListener(this);
        buttonBackActionBar.setVisibility(View.GONE);
        buttonCollectionActionBar.setVisibility(View.GONE);
    }

    @Override
    protected void getArgument() {
        Bundle bundle = getArguments();
        otherFoods = bundle.getParcelableArrayList(ARGUMENT_FOOD_LIST);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (otherFoods != null) {
            homePresenter.createDailyMenu(otherFoods);
            displayOtherFoodList();
            buttonSortOtherFood.setOnClickListener(this);
        }
    }

    @Override
    public void showDailyMenu(List<FoodDetail> dailyFoods) {

        FoodAdapter adapter = new FoodAdapter(FoodType.DAILY_MENU, this);
        recyclerViewDailyMenu.setAdapter(adapter);
        adapter.updateData(dailyFoods);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSortOtherFood: {
                displayOtherFood();
                break;
            }
            case R.id.buttonSearchActionBar: {
                controlDisplayFrameSearch();
                break;
            }
            case R.id.frameSearch: {
                controlDisplayFrameSearch();
                break;
            }
        }
    }

    private void displayOtherFood() {
        int icon;
        if (isSortList) {
            icon = R.drawable.ic_list;
            displayOtherFoodGrid();
        } else {
            icon = R.drawable.ic_grid;
        }
        buttonSortOtherFood.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
    }

    private void displayOtherFoodList() {
        FoodAdapter adapter = new FoodAdapter(FoodType.OTHER_LIST, this);
        recyclerViewOtherFood.setAdapter(adapter);
        recyclerViewOtherFood.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        );
        adapter.updateData(otherFoods);
        isSortList = true;
    }

    private void displayOtherFoodGrid() {
        FoodAdapter adapter = new FoodAdapter(FoodType.OTHER_GRID, this);
        recyclerViewOtherFood.setAdapter(adapter);
        recyclerViewOtherFood.setLayoutManager(
                new GridLayoutManager(getActivity(), 2)
        );
        adapter.updateData(otherFoods);
        isSortList = false;
    }

    @Override
    public void onFoodItemClick(FoodDetail foodDetail) {
        if (getActivity() != null) {
            startActivityForResult(
                    FoodDetailActivity.getIntent(getActivity(), foodDetail),
                    FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            );
        }
    }

    private void controlDisplayFrameSearch() {
        if (isShowFrameSearch) {
            hideFrameSearch();
            isShowFrameSearch = false;
        } else {
            displayFrameSearch();
            isShowFrameSearch = true;
        }
    }

    private SearchView searchViewHomeScreen;
    private ConstraintLayout frameSearch;

    private void hideFrameSearch() {
        searchViewHomeScreen.setQuery(null, false);
        frameSearch.setVisibility(View.GONE);
        isShowFrameSearch = false;
    }

    private void displayFrameSearch() {
        frameSearch.setVisibility(View.VISIBLE);
        frameSearch.setOnClickListener(this);
        searchViewHomeScreen.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        switchByAddFragment(SearchFragment.newInstance(query));
        hideFrameSearch();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
