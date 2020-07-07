package com.ptit.daily_food.data.repository;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.source.OnLoadedCallback;
import com.ptit.daily_food.data.source.RecipeDataSource;

import java.util.List;

public class RecipeRepository implements RecipeDataSource.Remote, RecipeDataSource.Local {

    private RecipeDataSource.Remote remoteDataSource;
    private RecipeDataSource.Local localDataSource;

    public RecipeRepository(
            RecipeDataSource.Remote remoteDataSource,
            RecipeDataSource.Local localDataSource
    ) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static RecipeRepository getInstance(
            RecipeDataSource.Remote remoteDataSource,
            RecipeDataSource.Local localDataSource
    ) {
        return new RecipeRepository(remoteDataSource, localDataSource);
    }

    @Override
    public void getRandomRecipes(OnLoadedCallback<List<FoodDetail>> callback) {
        remoteDataSource.getRandomRecipes(callback);
    }

    @Override
    public void getAllFavoriteFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        localDataSource.getAllFavoriteFoods(callback);
    }

    @Override
    public void getAllPartyFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        localDataSource.getAllPartyFoods(callback);
    }

    @Override
    public void getAllFamilyFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        localDataSource.getAllFamilyFoods(callback);
    }

    @Override
    public void getAllCookingFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        localDataSource.getAllCookingFoods(callback);
    }

    @Override
    public void addToFavorite(FoodDetail foodDetail) {
        localDataSource.addToFavorite(foodDetail);
    }

    @Override
    public void addToFamily(FoodDetail foodDetail) {
        localDataSource.addToFamily(foodDetail);
    }

    @Override
    public void addToParty(FoodDetail foodDetail) {
        localDataSource.addToParty(foodDetail);
    }

    @Override
    public void addToCooking(FoodDetail foodDetail) {
        localDataSource.addToCooking(foodDetail);
    }

    @Override
    public void deleteFoodFromFavorite(FoodDetail foodDetail) {

    }

    @Override
    public void deleteFoodFromFamily(FoodDetail foodDetail) {

    }

    @Override
    public void deleteFoodFromParty(FoodDetail foodDetail) {

    }

    @Override
    public void deleteFoodFromCooking(FoodDetail foodDetail) {
        localDataSource.deleteFoodFromCooking(foodDetail);
    }
}
