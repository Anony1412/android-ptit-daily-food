package com.ptit.daily_food.ui.fragment.collection;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.OnLoadedCallback;

import java.util.List;

public class CollectionPresenter implements CollectionContracts.Presenter {

    private CollectionContracts.View view;
    private RecipeRepository recipeRepository;

    public CollectionPresenter(CollectionContracts.View view, RecipeRepository recipeRepository) {
        this.view = view;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void getAllFavoriteFoods() {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                view.createFavoriteFoods(data);
            }

            @Override
            public void onFailure(Exception exception) {
                view.showError(exception);
            }
        };
        recipeRepository.getAllFavoriteFoods(callback);
    }

    @Override
    public void getAllFamilyFoods() {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                view.createFamilyFoods(data);
            }

            @Override
            public void onFailure(Exception exception) {
                view.showError(exception);
            }
        };
        recipeRepository.getAllFamilyFoods(callback);
    }

    @Override
    public void getAllPartyFoods() {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                view.createPartyFoods(data);
            }

            @Override
            public void onFailure(Exception exception) {
                view.showError(exception);
            }
        };
        recipeRepository.getAllPartyFoods(callback);
    }
}
