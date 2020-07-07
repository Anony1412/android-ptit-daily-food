package com.ptit.daily_food.ui.fragment.cooking;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface CookingContract {

    interface View {
        void showAllCookingFoods(List<FoodDetail> cookingFoods);
        void showError(Exception exception);
    }

    interface Presenter {
        void getAllCookingFoods();
        void deleteFoodFromCooking(FoodDetail foodDetail);
    }
}
