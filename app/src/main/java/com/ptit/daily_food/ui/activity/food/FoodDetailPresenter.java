package com.ptit.daily_food.ui.activity.food;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;

public class FoodDetailPresenter implements FoodDetailContract.Presenter {

    private RecipeRepository recipeRepository;

    public FoodDetailPresenter(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void addFoodToFavorite(FoodDetail foodDetail) {
        recipeRepository.addToFavorite(foodDetail);
    }

    @Override
    public void addFoodToFamily(FoodDetail foodDetail) {
        recipeRepository.addToFamily(foodDetail);
    }

    @Override
    public void addFoodToParty(FoodDetail foodDetail) {
        recipeRepository.addToParty(foodDetail);
    }

    @Override
    public void addFoodToCooking(FoodDetail foodDetail) {
        recipeRepository.addToCooking(foodDetail);
    }

    @Override
    public void deleteFoodFromCooking(FoodDetail foodDetail) {
        recipeRepository.deleteFoodFromCooking(foodDetail);
    }
}
