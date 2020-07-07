package com.ptit.daily_food.ui.fragment.cooking;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.OnLoadedCallback;

import java.util.List;

public class CookingPresenter implements CookingContract.Presenter {

    private CookingContract.View view;
    private RecipeRepository recipeRepository;

    public CookingPresenter(CookingContract.View view, RecipeRepository recipeRepository) {
        this.view = view;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void getAllCookingFoods() {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                view.showAllCookingFoods(data);
            }

            @Override
            public void onFailure(Exception exception) {
                view.showError(exception);
            }
        };
        recipeRepository.getAllCookingFoods(callback);
    }

    @Override
    public void deleteFoodFromCooking(FoodDetail foodDetail) {
        recipeRepository.deleteFoodFromCooking(foodDetail);
    }
}
