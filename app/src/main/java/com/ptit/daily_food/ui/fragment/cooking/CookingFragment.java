package com.ptit.daily_food.ui.fragment.cooking;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.local.RecipeLocalDataSource;
import com.ptit.daily_food.data.source.local.dao.FoodDao;
import com.ptit.daily_food.data.source.local.dao.FoodDaoImpl;
import com.ptit.daily_food.data.source.local.database.FoodDailyDatabase;
import com.ptit.daily_food.data.source.remote.RecipeRemoteDataSource;
import com.ptit.daily_food.ui.activity.food.FoodDetailActivity;
import com.ptit.daily_food.ui.adapter.FoodAdapter;
import com.ptit.daily_food.ui.base.BaseFragment;
import com.ptit.daily_food.ui.fragment.collection.CollectionFragment;
import com.ptit.daily_food.ui.interfaces.FoodItemClickInterface;
import com.ptit.daily_food.ultis.FoodType;

import java.util.List;

public class CookingFragment
        extends BaseFragment
        implements CookingContract.View, FoodItemClickInterface {

    private CookingPresenter cookingPresenter;
    private FoodAdapter cookingAdapter = new FoodAdapter(FoodType.COOKING, this);
    private TextView textActionBarTitle;
    private ImageButton buttonBackActionBar, buttonCollectionActionBar, buttonSearchActionBar;

    @Override
    protected void initView() {

    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.fragment_cooking;
    }

    @Override
    protected void initPresenter() {
        FoodDailyDatabase foodDailyDatabase = null;
        if (getContext() != null) {
            foodDailyDatabase = FoodDailyDatabase.getInstance(getContext());
        }
        FoodDao foodDAO = FoodDaoImpl.getInstance(foodDailyDatabase);
        RecipeRepository recipeRepository = RecipeRepository.getInstance(
                RecipeRemoteDataSource.getInstance(),
                RecipeLocalDataSource.getInstance(foodDAO)
        );
        cookingPresenter = new CookingPresenter(this, recipeRepository);
        cookingPresenter.getAllCookingFoods();
    }

    @Override
    protected void initActionBar() {
        if (getActivity() != null) {
            textActionBarTitle = getActivity().findViewById(R.id.textActionBarTitle);
            buttonBackActionBar = getActivity().findViewById(R.id.buttonBackActionBar);
            buttonCollectionActionBar = getActivity().findViewById(R.id.buttonCollectionActionBar);
            buttonSearchActionBar = getActivity().findViewById(R.id.buttonSearchActionBar);
        }
        textActionBarTitle.setText(getString(R.string.title_cooking));
        buttonBackActionBar.setVisibility(View.GONE);
        buttonCollectionActionBar.setVisibility(View.GONE);
        buttonSearchActionBar.setVisibility(View.GONE);
    }

    @Override
    protected void getArgument() {

    }

    private RecyclerView recyclerViewCookingFood;

    @Override
    public void showAllCookingFoods(List<FoodDetail> cookingFoods) {
        if (getActivity() != null) {
            recyclerViewCookingFood = getActivity().findViewById(R.id.recyclerViewCookingFood);
        }
        recyclerViewCookingFood.setAdapter(cookingAdapter);
        cookingAdapter.updateData(cookingFoods);
    }

    @Override
    public void showError(Exception exception) {
        Toast.makeText(
                getActivity(),
                getString(R.string.notification_update_data),
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
        && resultCode == Activity.RESULT_OK) {
            FoodDetail foodDetail = data.getParcelableExtra(FoodDetailActivity.RESULT_FOOD_ID);
            cookingAdapter.deleteItem(foodDetail);
        }
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

    public static CookingFragment newInstance() {
        return new CookingFragment();
    }
}
