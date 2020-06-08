package com.ptit.daily_food.data.source;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface SearchDataSource {
    interface Remote {
        void searchRecipeComplex(String keyword, OnLoadedCallback<List<FoodDetail>> callback);
        void searchRecipeById(String foodId, OnLoadedCallback<List<FoodDetail>> callback);
    }
}
