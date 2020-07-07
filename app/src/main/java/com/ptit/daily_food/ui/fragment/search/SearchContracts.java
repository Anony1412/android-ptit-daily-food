package com.ptit.daily_food.ui.fragment.search;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface SearchContracts {

    interface View {
        void showRecipeComplex(List<FoodDetail> recipes);
        void showRecipeById(List<FoodDetail> recipes);
        void showError(Exception exception);
    }

    interface Presenter {
        void searchRecipeComplex(String keyword);
        void searchRecipeById(String foodId);
    }
}
