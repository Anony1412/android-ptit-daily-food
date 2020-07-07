package com.ptit.daily_food.data.repository;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.source.OnLoadedCallback;
import com.ptit.daily_food.data.source.SearchDataSource;

import java.util.List;

public class SearchRepository implements SearchDataSource.Remote {

    private SearchDataSource.Remote remoteDataSource;

    public SearchRepository(SearchDataSource.Remote remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void searchRecipeComplex(String keyword, OnLoadedCallback<List<FoodDetail>> callback) {
        remoteDataSource.searchRecipeComplex(keyword, callback);
    }

    @Override
    public void searchRecipeById(String foodId, OnLoadedCallback<List<FoodDetail>> callback) {
        remoteDataSource.searchRecipeById(foodId, callback);
    }
}
