package com.ptit.daily_food.ui.activity.splash;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.OnLoadedCallback;

import java.util.List;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View splashView;
    private RecipeRepository recipeRepository;

    public SplashPresenter(SplashContract.View splashView, RecipeRepository recipeRepository) {
        this.splashView = splashView;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void getRandomFoods() {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                splashView.onTransportDataToHome(data);
            }

            @Override
            public void onFailure(Exception exception) {
                splashView.showError(exception);
            }
        };
        recipeRepository.getRandomRecipes(callback);
    }
}
