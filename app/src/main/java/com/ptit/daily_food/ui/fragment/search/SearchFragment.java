package com.ptit.daily_food.ui.fragment.search;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.SearchRepository;
import com.ptit.daily_food.data.source.remote.SearchRemoteDataSource;
import com.ptit.daily_food.ui.activity.food.FoodDetailActivity;
import com.ptit.daily_food.ui.adapter.FoodAdapter;
import com.ptit.daily_food.ui.base.BaseFragment;
import com.ptit.daily_food.ui.interfaces.FoodItemClickInterface;
import com.ptit.daily_food.ultis.FoodType;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment
        extends BaseFragment
        implements SearchContracts.View, FoodItemClickInterface {

    private static final String ARGUMENT_SEARCH_KEY = "ARGUMENT_SEARCH_KEY";

    private String searchKey;
    private FoodAdapter searchAdapter =
            new FoodAdapter(FoodType.SEARCH_RESULT, this);
    private SearchPresenter searchPresenter;
    private ConstraintLayout frameLoading;
    private ImageView imageLoadSearchResult;
    private RecyclerView recyclerViewSearchResult;
    private TextView textActionBarTitle;
    private ImageButton buttonSearchActionBar, buttonCollectionActionBar;
    private ImageButton buttonBackActionBar;

    public static SearchFragment newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_SEARCH_KEY, query);
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    @Override
    protected void initView() {
        if (getActivity() != null) {
            frameLoading = getActivity().findViewById(R.id.frameLoading);
            imageLoadSearchResult = getActivity().findViewById(R.id.imageLoadSearchResult);
            recyclerViewSearchResult = getActivity().findViewById(R.id.recyclerViewSearchResult);
        }
        Animation animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate);
        frameLoading.setVisibility(View.VISIBLE);
        imageLoadSearchResult.startAnimation(animRotate);
        recyclerViewSearchResult.setAdapter(searchAdapter);
    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.fragment_search;
    }

    @Override
    protected void initPresenter() {
        SearchRepository searchRepository = new SearchRepository(new SearchRemoteDataSource());
        searchPresenter = new SearchPresenter(this, searchRepository);
        searchPresenter.searchRecipeComplex(searchKey);
    }

    private void initListener() {
        if (getActivity() != null) {
            buttonBackActionBar = getActivity().findViewById(R.id.buttonBackActionBar);
        }
        buttonBackActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment(SearchFragment.this);
            }
        });
    }

    @Override
    protected void initActionBar() {
        if (getActivity() != null) {
            textActionBarTitle = getActivity().findViewById(R.id.textActionBarTitle);
            buttonSearchActionBar = getActivity().findViewById(R.id.buttonSearchActionBar);
            buttonCollectionActionBar = getActivity().findViewById(R.id.buttonCollectionActionBar);
        }
        textActionBarTitle.setText(getString(R.string.title_search));
        buttonSearchActionBar.setVisibility(View.GONE);
        buttonCollectionActionBar.setVisibility(View.GONE);
    }

    @Override
    protected void getArgument() {
        Bundle bundle = getArguments();
        searchKey = bundle.getString(ARGUMENT_SEARCH_KEY);
    }

    @Override
    public void showRecipeComplex(List<FoodDetail> recipes) {
        frameLoading.setVisibility(View.GONE);
        searchAdapter.updateData(recipes);
    }

    @Override
    public void showRecipeById(List<FoodDetail> recipes) {
        if (getActivity() != null) {
            startActivityForResult(
                    FoodDetailActivity.getIntent(getActivity(), recipes.get(0)),
                    FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            );
        }
    }

    @Override
    public void showError(Exception exception) {
        Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFoodItemClick(FoodDetail foodDetail) {
        searchPresenter.searchRecipeById(foodDetail.getId().toString());
    }
}
